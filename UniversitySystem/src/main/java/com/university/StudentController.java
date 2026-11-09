package com.university;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;

/**
 * Controller providing full CRUD (Create, Read, Update, Delete) capabilities
 * for student records in Neon PostgreSQL, with live search and non-blocking background concurrency.
 */
public class StudentController {

    // Form input fields
    @FXML private TextField regField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField searchField;

    // Action buttons
    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button clearButton;
    @FXML private Button refreshButton;

    // Table and Columns
    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, Integer> colId;
    @FXML private TableColumn<Student, String> colReg;
    @FXML private TableColumn<Student, String> colFirst;
    @FXML private TableColumn<Student, String> colLast;
    @FXML private TableColumn<Student, String> colEmail;
    @FXML private TableColumn<Student, String> colProgram;

    // Status bar labels
    @FXML private Label statusLabel;
    @FXML private Label countLabel;

    private final ObservableList<Student> studentList = FXCollections.observableArrayList();
    private FilteredList<Student> filteredStudents;
    private Student selectedStudent = null;

    @FXML
    public void initialize() {
        // 1. Bind TableColumns to Student observable properties
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colReg.setCellValueFactory(cellData -> cellData.getValue().regNumberProperty());
        colFirst.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        colLast.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        colEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        colProgram.setCellValueFactory(cellData -> cellData.getValue().programNameProperty());

        // 2. Setup live search filtering (FilteredList wraps master studentList)
        filteredStudents = new FilteredList<>(studentList, p -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredStudents.setPredicate(student -> {
                if (newValue == null || newValue.isBlank()) {
                    return true;
                }
                String lower = newValue.toLowerCase().trim();
                return student.getRegNumber().toLowerCase().contains(lower) ||
                       student.getFirstName().toLowerCase().contains(lower) ||
                       student.getLastName().toLowerCase().contains(lower) ||
                       student.getEmail().toLowerCase().contains(lower) ||
                       student.getProgramName().toLowerCase().contains(lower);
            });
            updateCountLabel();
        });

        // 3. Wrap filtered list in a SortedList so column clicking still sorts
        SortedList<Student> sortedStudents = new SortedList<>(filteredStudents);
        sortedStudents.comparatorProperty().bind(studentTable.comparatorProperty());
        studentTable.setItems(sortedStudents);

        // 4. Input validation: Disable 'Add Student' if mandatory fields are empty
        addButton.disableProperty().bind(
            regField.textProperty().isEmpty()
                .or(firstNameField.textProperty().isEmpty())
                .or(lastNameField.textProperty().isEmpty())
        );

        // 5. Initial state for Update & Delete (disabled until a student row is selected)
        updateButton.setDisable(true);
        deleteButton.setDisable(true);

        // 6. Table selection listener: populate form fields when a row is clicked
        studentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                selectedStudent = newVal;
                regField.setText(newVal.getRegNumber());
                firstNameField.setText(newVal.getFirstName());
                lastNameField.setText(newVal.getLastName());
                emailField.setText(newVal.getEmail());
                updateButton.setDisable(false);
                deleteButton.setDisable(false);
                statusLabel.setText("Selected: " + newVal.getFirstName() + " " + newVal.getLastName() + " (" + newVal.getRegNumber() + ")");
            } else {
                selectedStudent = null;
                updateButton.setDisable(true);
                deleteButton.setDisable(true);
            }
        });

        // 7. Load all students asynchronously from Neon PostgreSQL
        loadStudents();
    }

    /**
     * CREATE: Inserts a new student into Neon PostgreSQL on a background worker thread.
     */
    @FXML
    public void handleAddStudent() {
        String reg = regField.getText().trim();
        String fName = firstNameField.getText().trim();
        String lName = lastNameField.getText().trim();
        String mail = emailField.getText().trim();
        if (mail.isEmpty()) {
            mail = String.format("%s.%s@student.university.ac.ke", fName.toLowerCase(), lName.toLowerCase());
        }

        final String finalEmail = mail;
        statusLabel.setText("Adding student to Neon PostgreSQL...");

        Task<Void> addTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                String sql = "INSERT INTO students (registration_number, first_name, last_name, email, program_id) " +
                             "VALUES (?, ?, ?, ?, (SELECT program_id FROM programs LIMIT 1)) " +
                             "ON CONFLICT (registration_number) DO UPDATE SET " +
                             "first_name = EXCLUDED.first_name, last_name = EXCLUDED.last_name, email = EXCLUDED.email";

                try (Connection conn = DatabaseUtil.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, reg);
                    stmt.setString(2, fName);
                    stmt.setString(3, lName);
                    stmt.setString(4, finalEmail);
                    stmt.executeUpdate();
                }
                return null;
            }
        };

        addTask.setOnSucceeded(e -> {
            statusLabel.setText("✓ Student " + reg + " added successfully!");
            handleClearForm();
            loadStudents();
        });

        addTask.setOnFailed(e -> {
            Throwable ex = addTask.getException();
            statusLabel.setText("✗ Error adding student: " + (ex != null ? ex.getMessage() : "Unknown error"));
        });

        new Thread(addTask).start();
    }

    /**
     * UPDATE: Modifies the currently selected student in Neon PostgreSQL on a background thread.
     */
    @FXML
    public void handleUpdateStudent() {
        if (selectedStudent == null) {
            statusLabel.setText("Please select a student from the table to update.");
            return;
        }

        int studentId = selectedStudent.getId();
        String reg = regField.getText().trim();
        String fName = firstNameField.getText().trim();
        String lName = lastNameField.getText().trim();
        String mail = emailField.getText().trim();
        if (mail.isEmpty()) {
            mail = String.format("%s.%s@student.university.ac.ke", fName.toLowerCase(), lName.toLowerCase());
        }

        final String finalEmail = mail;
        statusLabel.setText("Updating student ID " + studentId + " in Neon PostgreSQL...");

        Task<Void> updateTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                String sql = "UPDATE students SET registration_number = ?, first_name = ?, last_name = ?, email = ? WHERE student_id = ?";
                try (Connection conn = DatabaseUtil.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, reg);
                    stmt.setString(2, fName);
                    stmt.setString(3, lName);
                    stmt.setString(4, finalEmail);
                    stmt.setInt(5, studentId);
                    stmt.executeUpdate();
                }
                return null;
            }
        };

        updateTask.setOnSucceeded(e -> {
            statusLabel.setText("✓ Student " + reg + " updated successfully!");
            handleClearForm();
            loadStudents();
        });

        updateTask.setOnFailed(e -> {
            Throwable ex = updateTask.getException();
            statusLabel.setText("✗ Error updating student: " + (ex != null ? ex.getMessage() : "Unknown error"));
        });

        new Thread(updateTask).start();
    }

    /**
     * DELETE: Deletes the currently selected student from Neon PostgreSQL after confirmation.
     */
    @FXML
    public void handleDeleteStudent() {
        if (selectedStudent == null) {
            statusLabel.setText("Please select a student from the table to delete.");
            return;
        }

        int studentId = selectedStudent.getId();
        String reg = selectedStudent.getRegNumber();
        String fullName = selectedStudent.getFirstName() + " " + selectedStudent.getLastName();

        // Confirmation Dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Delete Student Record?");
        alert.setContentText("Are you sure you want to permanently delete " + fullName + " (" + reg + ") from Neon PostgreSQL?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) {
            statusLabel.setText("Deletion cancelled.");
            return;
        }

        statusLabel.setText("Deleting student " + reg + " from database...");

        Task<Void> deleteTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                String sql = "DELETE FROM students WHERE student_id = ?";
                try (Connection conn = DatabaseUtil.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, studentId);
                    stmt.executeUpdate();
                }
                return null;
            }
        };

        deleteTask.setOnSucceeded(e -> {
            statusLabel.setText("✓ Student " + reg + " deleted from database.");
            handleClearForm();
            loadStudents();
        });

        deleteTask.setOnFailed(e -> {
            Throwable ex = deleteTask.getException();
            statusLabel.setText("✗ Error deleting student: " + (ex != null ? ex.getMessage() : "Unknown error"));
        });

        new Thread(deleteTask).start();
    }

    /**
     * Clears form inputs and resets table selection.
     */
    @FXML
    public void handleClearForm() {
        studentTable.getSelectionModel().clearSelection();
        selectedStudent = null;
        regField.clear();
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
        statusLabel.setText("Form cleared. Ready.");
    }

    /**
     * Refreshes the list by re-fetching from Neon PostgreSQL.
     */
    @FXML
    public void handleRefresh() {
        statusLabel.setText("Refreshing from Neon PostgreSQL...");
        loadStudents();
    }

    /**
     * READ: Loads all students asynchronously on a background thread.
     */
    public void loadStudents() {
        LoadStudentsTask task = new LoadStudentsTask();

        task.setOnSucceeded(e -> {
            studentList.setAll(task.getValue());
            updateCountLabel();
            statusLabel.setText("✓ Loaded " + studentList.size() + " active student records from Neon PostgreSQL.");
        });

        task.setOnFailed(e -> {
            Throwable ex = task.getException();
            statusLabel.setText("✗ Error loading students: " + (ex != null ? ex.getMessage() : "Unknown error"));
        });

        new Thread(task).start();
    }

    private void updateCountLabel() {
        if (countLabel != null) {
            int showing = filteredStudents != null ? filteredStudents.size() : studentList.size();
            int total = studentList.size();
            if (showing == total) {
                countLabel.setText(total + " students total");
            } else {
                countLabel.setText("Showing " + showing + " of " + total + " students");
            }
        }
    }
}

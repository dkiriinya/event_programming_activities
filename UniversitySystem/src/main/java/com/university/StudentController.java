package com.university;

import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controller providing full CRUD (Create, Read, Update, Bulk Delete) capabilities
 * for student records in Neon PostgreSQL, featuring a shaded loading overlay,
 * live search filtering, multi-selection, and non-blocking background concurrency.
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

    // Loading Shader / Scrim Overlay
    @FXML private VBox loadingOverlay;
    @FXML private ProgressIndicator loadingSpinner;
    @FXML private Label loadingTitle;
    @FXML private Label loadingSubtitle;

    // Status bar labels
    @FXML private Label statusLabel;
    @FXML private Label countLabel;

    private final ObservableList<Student> studentList = FXCollections.observableArrayList();
    private FilteredList<Student> filteredStudents;
    private Student selectedStudent = null;

    @FXML
    public void initialize() {
        // 1. Configure TableView multi-selection mode for bulk delete
        studentTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // 2. Bind TableColumns to Student observable properties
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colReg.setCellValueFactory(cellData -> cellData.getValue().regNumberProperty());
        colFirst.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        colLast.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        colEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        colProgram.setCellValueFactory(cellData -> cellData.getValue().programNameProperty());

        // Setup placeholder for empty search results
        Label placeholder = new Label("No matching students found in directory.");
        placeholder.setStyle("-fx-text-fill: #95a5a6; -fx-font-size: 13px; -fx-font-style: italic;");
        studentTable.setPlaceholder(placeholder);

        // 3. Setup live search filtering (FilteredList wraps master studentList)
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

        // 4. Wrap filtered list in a SortedList so column clicking still sorts
        SortedList<Student> sortedStudents = new SortedList<>(filteredStudents);
        sortedStudents.comparatorProperty().bind(studentTable.comparatorProperty());
        studentTable.setItems(sortedStudents);

        // 5. Reactive validation: 'Add Student' is DISABLED whenever:
        //    (a) An existing student is currently selected in the table (prevents adding duplicate info), OR
        //    (b) Any mandatory text field (reg, first name, last name) is empty.
        BooleanBinding hasSelection = Bindings.isNotEmpty(studentTable.getSelectionModel().getSelectedItems());
        BooleanBinding fieldsIncomplete = regField.textProperty().isEmpty()
            .or(firstNameField.textProperty().isEmpty())
            .or(lastNameField.textProperty().isEmpty());

        addButton.disableProperty().bind(hasSelection.or(fieldsIncomplete));

        // 6. Initial state for Update & Delete (disabled until a student row is selected)
        updateButton.setDisable(true);
        deleteButton.setDisable(true);

        // 7. Multi-selection listener: handles single selection, bulk selection, and form synchronization
        studentTable.getSelectionModel().getSelectedItems().addListener((ListChangeListener<Student>) c -> {
            ObservableList<Student> selected = studentTable.getSelectionModel().getSelectedItems();
            int count = selected.size();

            if (count == 0) {
                selectedStudent = null;
                updateButton.setDisable(true);
                deleteButton.setDisable(true);
                deleteButton.setText("🗑️ Delete");
                statusLabel.setText("Ready to add a new student.");
            } else if (count == 1) {
                selectedStudent = selected.get(0);
                regField.setText(selectedStudent.getRegNumber());
                firstNameField.setText(selectedStudent.getFirstName());
                lastNameField.setText(selectedStudent.getLastName());
                emailField.setText(selectedStudent.getEmail());
                updateButton.setDisable(false);
                deleteButton.setDisable(false);
                deleteButton.setText("🗑️ Delete");
                statusLabel.setText("Selected: " + selectedStudent.getFirstName() + " " + selectedStudent.getLastName() + " (" + selectedStudent.getRegNumber() + ") — Add is disabled for existing records.");
            } else {
                // Multiple rows selected: Enable Bulk Delete, disable single-edit
                selectedStudent = null;
                updateButton.setDisable(true);
                deleteButton.setDisable(false);
                deleteButton.setText(String.format("🗑️ Bulk Delete (%d)", count));
                statusLabel.setText(String.format("Selected %d students for bulk deletion (Hold Ctrl or Shift to select more).", count));
            }
        });

        // 8. Load all students asynchronously from Neon PostgreSQL with visual loading shader
        loadStudents();
    }

    /**
     * Displays the shaded overlay with custom headline and subtitle.
     */
    private void showLoading(String title, String subtitle) {
        if (loadingOverlay != null) {
            if (loadingTitle != null) loadingTitle.setText(title);
            if (loadingSubtitle != null) loadingSubtitle.setText(subtitle);
            loadingOverlay.setOpacity(0.0);
            loadingOverlay.setVisible(true);
            loadingOverlay.setManaged(true);

            FadeTransition ft = new FadeTransition(Duration.millis(180), loadingOverlay);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
        }
    }

    /**
     * Smoothly fades out and conceals the shaded overlay.
     */
    private void hideLoading() {
        if (loadingOverlay != null && loadingOverlay.isVisible()) {
            FadeTransition ft = new FadeTransition(Duration.millis(220), loadingOverlay);
            ft.setFromValue(1.0);
            ft.setToValue(0.0);
            ft.setOnFinished(e -> {
                loadingOverlay.setVisible(false);
                loadingOverlay.setManaged(false);
                loadingOverlay.setOpacity(1.0);
            });
            ft.play();
        }
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
        showLoading("Adding Student...", "Inserting " + reg + " into Neon PostgreSQL...");

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
            hideLoading();
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
            statusLabel.setText("Please select a single student from the table to update.");
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
        showLoading("Updating Student...", "Writing changes for " + reg + " to Neon...");

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
            hideLoading();
            Throwable ex = updateTask.getException();
            statusLabel.setText("✗ Error updating student: " + (ex != null ? ex.getMessage() : "Unknown error"));
        });

        new Thread(updateTask).start();
    }

    /**
     * DELETE / BULK DELETE: Deletes one or more selected students from Neon PostgreSQL after confirmation.
     */
    @FXML
    public void handleDeleteStudent() {
        ObservableList<Student> selected = studentTable.getSelectionModel().getSelectedItems();
        if (selected == null || selected.isEmpty()) {
            statusLabel.setText("Please select one or more students from the table to delete.");
            return;
        }

        List<Student> toDelete = new ArrayList<>(selected);
        int deleteCount = toDelete.size();

        // Confirmation Dialog (single vs bulk)
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        if (deleteCount == 1) {
            Student s = toDelete.get(0);
            alert.setHeaderText("Delete Student Record?");
            alert.setContentText("Are you sure you want to permanently delete " + s.getFirstName() + " " + s.getLastName() + " (" + s.getRegNumber() + ") from Neon PostgreSQL?");
        } else {
            alert.setHeaderText("Bulk Delete " + deleteCount + " Students?");
            alert.setContentText("Are you sure you want to permanently delete " + deleteCount + " selected student records from Neon PostgreSQL?\nThis operation cannot be undone.");
        }

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) {
            statusLabel.setText("Deletion cancelled.");
            return;
        }

        statusLabel.setText("Deleting " + deleteCount + " student(s) from database...");
        showLoading("Deleting Student(s)...", "Permanently removing " + deleteCount + " student record(s) from Neon...");

        Task<Integer> deleteTask = new Task<>() {
            @Override
            protected Integer call() throws Exception {
                String sql = "DELETE FROM students WHERE student_id = ?";
                int deleted = 0;
                try (Connection conn = DatabaseUtil.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {
                    for (Student s : toDelete) {
                        stmt.setInt(1, s.getId());
                        stmt.addBatch();
                    }
                    int[] batchResults = stmt.executeBatch();
                    for (int r : batchResults) {
                        if (r >= 0 || r == Statement.SUCCESS_NO_INFO) {
                            deleted++;
                        }
                    }
                }
                return deleted;
            }
        };

        deleteTask.setOnSucceeded(e -> {
            int count = deleteTask.getValue();
            statusLabel.setText("✓ Successfully deleted " + count + " student record(s) from Neon PostgreSQL.");
            handleClearForm();
            loadStudents();
        });

        deleteTask.setOnFailed(e -> {
            hideLoading();
            Throwable ex = deleteTask.getException();
            statusLabel.setText("✗ Error deleting students: " + (ex != null ? ex.getMessage() : "Unknown error"));
        });

        new Thread(deleteTask).start();
    }

    /**
     * Clears form inputs and resets table selection so the user can enter a new student.
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
        deleteButton.setText("🗑️ Delete");
        statusLabel.setText("Form cleared. Ready to enter a new student.");
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
     * READ: Loads all students asynchronously on a background thread with visual loading shader.
     */
    public void loadStudents() {
        showLoading("Connecting to Neon Cloud...", "Fetching 100+ active student records over SSL...");

        LoadStudentsTask task = new LoadStudentsTask();

        task.setOnSucceeded(e -> {
            studentList.setAll(task.getValue());
            updateCountLabel();
            statusLabel.setText("✓ Loaded " + studentList.size() + " active student records from Neon PostgreSQL.");
            hideLoading();
        });

        task.setOnFailed(e -> {
            hideLoading();
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

package com.university;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Controller handling user interactions and asynchronous tasks for Student Management.
 */
public class StudentController {

    @FXML private TextField regField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, String> colReg;
    @FXML private TableColumn<Student, String> colFirst;
    @FXML private TableColumn<Student, String> colLast;
    @FXML private Label statusLabel;
    @FXML private Button saveButton;

    private final ObservableList<Student> studentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Bind TableColumns to Student properties
        colReg.setCellValueFactory(cellData -> cellData.getValue().regNumberProperty());
        colFirst.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        colLast.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        studentTable.setItems(studentList);

        // Disable Save button if required fields are empty
        saveButton.disableProperty().bind(
            regField.textProperty().isEmpty()
                .or(firstNameField.textProperty().isEmpty())
                .or(lastNameField.textProperty().isEmpty())
        );

        // Load initial records from Neon PostgreSQL
        loadStudents();
    }

    @FXML
    private void handleSaveStudent() {
        String reg = regField.getText().trim();
        String fName = firstNameField.getText().trim();
        String lName = lastNameField.getText().trim();

        statusLabel.setText("Saving student to Neon PostgreSQL...");

        Task<Void> saveTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                String sql = "INSERT INTO students (registration_number, first_name, last_name, email) VALUES (?, ?, ?, ?) " +
                             "ON CONFLICT (registration_number) DO UPDATE SET first_name = EXCLUDED.first_name, last_name = EXCLUDED.last_name";

                try (Connection conn = DatabaseUtil.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, reg);
                    stmt.setString(2, fName);
                    stmt.setString(3, lName);
                    stmt.setString(4, reg.toLowerCase() + "@university.edu");
                    stmt.executeUpdate();
                }
                return null;
            }
        };

        saveTask.setOnSucceeded(e -> {
            statusLabel.setText("Student saved successfully!");
            regField.clear();
            firstNameField.clear();
            lastNameField.clear();
            loadStudents();
        });

        saveTask.setOnFailed(e -> {
            Throwable ex = saveTask.getException();
            statusLabel.setText("Error saving student: " + (ex != null ? ex.getMessage() : "Unknown error"));
        });

        new Thread(saveTask).start();
    }

    public void loadStudents() {
        LoadStudentsTask task = new LoadStudentsTask();

        task.setOnSucceeded(e -> {
            studentList.setAll(task.getValue());
            statusLabel.setText("Loaded " + studentList.size() + " students from Neon PostgreSQL.");
        });

        task.setOnFailed(e -> {
            Throwable ex = task.getException();
            statusLabel.setText("Error loading students: " + (ex != null ? ex.getMessage() : "Unknown error"));
        });

        new Thread(task).start();
    }
}

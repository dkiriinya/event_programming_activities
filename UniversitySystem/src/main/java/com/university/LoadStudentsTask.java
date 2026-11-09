package com.university;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Background Task handling non-blocking database queries (Producer-Consumer Pattern).
 * Fetches all student records with linked program names off the JavaFX UI thread.
 */
public class LoadStudentsTask extends Task<ObservableList<Student>> {

    @Override
    protected ObservableList<Student> call() throws Exception {
        ObservableList<Student> students = FXCollections.observableArrayList();
        String sql = "SELECT s.student_id, s.registration_number, s.first_name, s.last_name, s.email, " +
                     "COALESCE(p.program_name, 'General') AS program_name " +
                     "FROM students s " +
                     "LEFT JOIN programs p ON s.program_id = p.program_id " +
                     "ORDER BY s.student_id DESC";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Student student = new Student(
                    rs.getInt("student_id"),
                    rs.getString("registration_number"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("program_name")
                );
                students.add(student);
            }
        }

        return students;
    }
}

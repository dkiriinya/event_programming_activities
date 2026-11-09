package com.university;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;

/**
 * Controller for navigation shell and content area swapping.
 */
public class MainController {

    @FXML private StackPane centerPane;

    @FXML
    public void initialize() {
        showDashboard();
    }

    @FXML
    private void showDashboard() {
        Label welcome = new Label("Welcome to the University Dashboard!\nConnected to Neon PostgreSQL");
        welcome.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-alignment: center;");
        centerPane.getChildren().setAll(welcome);
    }

    @FXML
    private void showStudents() {
        try {
            Parent studentView = FXMLLoader.load(getClass().getResource("StudentView.fxml"));
            centerPane.getChildren().setAll(studentView);
        } catch (IOException e) {
            e.printStackTrace();
            centerPane.getChildren().setAll(new Label("Error loading StudentView: " + e.getMessage()));
        }
    }
}

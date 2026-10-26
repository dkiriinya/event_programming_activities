package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomEventDemo extends Application {

    @Override
    public void start(Stage primaryStage) {
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter username");

        Button loginBtn = new Button("Login");
        Label statusLabel = new Label("Please log in.");

        VBox root = new VBox(15, usernameField, loginBtn, statusLabel);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // ---- FIRING THE CUSTOM EVENT ----
        loginBtn.setOnAction(e -> {
            String username = usernameField.getText();
            if (!username.isEmpty()) {
                // Fire the custom event on the root node!
                root.fireEvent(new LoginSuccessEvent(username));
            } else {
                statusLabel.setText("Username cannot be empty.");
            }
        });

        // ---- LISTENING FOR THE CUSTOM EVENT ----
        root.addEventHandler(LoginSuccessEvent.LOGIN_SUCCESS, event -> {
            // This handler is completely decoupled from the button logic!
            System.out.println("Custom Event Received! User logged in: " + event.getUsername());
            statusLabel.setText("Welcome back, " + event.getUsername() + "!");

            // Example of another action triggered by the event
            usernameField.clear();
        });

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Custom Event Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

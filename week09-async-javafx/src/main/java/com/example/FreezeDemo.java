package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FreezeDemo extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label statusLabel = new Label("Ready");
        Button freezeButton = new Button("Freeze the UI!");

        // THIS IS THE BAD WAY.
        // We are calling Thread.sleep() directly inside the Event Handler.
        // This blocks the JavaFX Application Thread!
        freezeButton.setOnAction(e -> {
            statusLabel.setText("Processing...");
            try {
                Thread.sleep(5000); // Simulate a 5-second database query or download
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            statusLabel.setText("Done!");
        });

        VBox root = new VBox(20, freezeButton, statusLabel);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(root, 400, 200);
        primaryStage.setTitle("The Freeze Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package com.example;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProgressDemo extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label statusLabel = new Label("Ready");
        ProgressBar progressBar = new ProgressBar(0); // Starts at 0%
        progressBar.setPrefWidth(300);

        Button startButton = new Button("Download File");

        startButton.setOnAction(e -> {
            startButton.setDisable(true);

            // 1. Create the Task
            Task<Void> downloadTask = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    // Simulate downloading 100 chunks of data
                    for (int i = 0; i <= 100; i++) {
                        Thread.sleep(50); // Simulate network delay

                        // 2. Safely update the task's built-in properties
                        // These automatically sync with the UI thread!
                        updateProgress(i, 100);
                        updateMessage("Downloading... " + i + "%");
                    }
                    return null;
                }
            };

            // 3. Bind the UI components to the task's properties
            // This is MUCH cleaner than using Platform.runLater() for progress!
            progressBar.progressProperty().bind(downloadTask.progressProperty());
            statusLabel.textProperty().bind(downloadTask.messageProperty());

            // 4. When done, clean up
            downloadTask.setOnSucceeded(event -> {
                startButton.setDisable(false);
                // Unbind the label so we can set a final message manually
                statusLabel.textProperty().unbind();
                statusLabel.setText("Download Complete!");
            });

            // 5. Start the task
            Thread thread = new Thread(downloadTask);
            thread.setDaemon(true);
            thread.start();
        });

        VBox root = new VBox(20, startButton, progressBar, statusLabel);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-alignment: center;");

        Scene scene = new Scene(root, 400, 200);
        primaryStage.setTitle("Progress Bar Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

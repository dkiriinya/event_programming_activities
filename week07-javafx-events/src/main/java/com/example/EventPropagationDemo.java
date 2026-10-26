package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EventPropagationDemo extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label logLabel = new Label("Click the button...");
        Button btn = new Button("Click Me!");
        VBox root = new VBox(20, logLabel, btn);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // 1. EVENT FILTER (Capturing Phase - Top Down)
        // This fires BEFORE the event reaches the button
        root.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED, e -> {
            System.out.println("1. [FILTER] Root intercepted the click!");
            // Uncomment the line below to stop the event from reaching the button!
            // e.consume();
        });

        // 2. EVENT HANDLER ON ROOT (Bubbling Phase - Bottom Up)
        // This fires AFTER the button handles the event, as it bubbles up
        root.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, e -> {
            System.out.println("3. [HANDLER] Root saw the click bubbling up!");
        });

        // 3. EVENT HANDLER ON BUTTON (Bubbling Phase - Target)
        btn.setOnMouseClicked(e -> {
            System.out.println("2. [HANDLER] Button was clicked!");
            logLabel.setText("Button was clicked!");
        });

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Event Propagation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

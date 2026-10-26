package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MouseKeyDemo extends Application {

    private double x = 250;
    private double y = 200;
    private final double STEP = 15; // How far to move per key press

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        root.setStyle("-fx-background-color: #f4f4f4;");

        // The player character
        Circle player = new Circle(x, y, 20, Color.BLUE);

        // A text label to show mouse coordinates
        Text mouseCoords = new Text(10, 20, "Mouse Coordinates: X=0, Y=0");
        root.getChildren().addAll(player, mouseCoords);

        // ---- MOUSE EVENTS ----
        // Track mouse movement over the whole pane
        root.setOnMouseMoved(e -> {
            mouseCoords.setText("Mouse Coordinates: X=" + e.getX() + ", Y=" + e.getY());
        });

        // ---- KEY EVENTS ----
        // Note: Key events need focus. In JavaFX, setFocusTraversable(true) allows the pane to receive focus.
        root.setFocusTraversable(true);

        root.setOnKeyPressed(e -> {
            KeyCode key = e.getCode();
            if (key == KeyCode.UP) y -= STEP;
            if (key == KeyCode.DOWN) y += STEP;
            if (key == KeyCode.LEFT) x -= STEP;
            if (key == KeyCode.RIGHT) x += STEP;

            // Keep the circle inside the window bounds
            x = Math.max(20, Math.min(x, 480));
            y = Math.max(20, Math.min(y, 380));

            player.setCenterX(x);
            player.setCenterY(y);
        });

        Scene scene = new Scene(root, 500, 400);

        // Ensure the pane gets focus as soon as the app starts
        primaryStage.setOnShown(e -> root.requestFocus());

        primaryStage.setTitle("Mouse and Key Events");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

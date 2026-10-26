package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class DragDropDemo extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 1. Create the Drag Source (The Item to move)
        Label itemToDrag = new Label("Drag Me 📦");
        itemToDrag.setStyle("-fx-background-color: #f39c12; -fx-padding: 10; -fx-font-size: 16px; -fx-text-fill: white;");

        // 2. Create the Drop Target (The Zone)
        Rectangle dropZone = new Rectangle(200, 100);
        dropZone.setFill(Color.LIGHTGRAY);
        dropZone.setStroke(Color.GRAY);
        dropZone.setStrokeWidth(2);

        Label dropLabel = new Label("Drop Here!");

        // Container for the drop zone
        VBox dropContainer = new VBox(10, dropZone, dropLabel);
        dropContainer.setStyle("-fx-alignment: center;");

        // ---- DRAG DETECTED (Source) ----
        itemToDrag.setOnDragDetected(event -> {
            Dragboard db = itemToDrag.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(itemToDrag.getText());
            db.setContent(content);
            event.consume();
        });

        // ---- DRAG OVER (Target) ----
        // Tells JavaFX that this node accepts dragged items
        dropZone.setOnDragOver(event -> {
            if (event.getGestureSource() != dropZone && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        // ---- DRAG ENTERED (Visual Feedback) ----
        dropZone.setOnDragEntered(event -> {
            if (event.getGestureSource() != dropZone && event.getDragboard().hasString()) {
                dropZone.setFill(Color.LIGHTGREEN); // Change color when hovering
            }
        });

        // ---- DRAG EXITED (Reset Visual Feedback) ----
        dropZone.setOnDragExited(event -> {
            dropZone.setFill(Color.LIGHTGRAY); // Reset color
        });

        // ---- DRAG DROPPED (Target) ----
        dropZone.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;

            if (db.hasString()) {
                // Handle the drop!
                dropLabel.setText("Dropped: " + db.getString());
                itemToDrag.setDisable(true); // Disable the item after drop
                success = true;
            }

            event.setDropCompleted(success);
            event.consume();
        });

        VBox root = new VBox(30, itemToDrag, dropContainer);
        root.setStyle("-fx-padding: 30; -fx-alignment: center;");

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Drag and Drop Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

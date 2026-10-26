package programmaticdemo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Activity 2: JavaFX Layouts (Programmatic)
 * Demonstrates layout panes: HBox, VBox, GridPane, assembled within a BorderPane.
 */
public class LayoutDemo extends Application {

    @Override
    public void start(Stage primaryStage) {
        // ---- 1. TOP: HBox (Horizontal Layout) ----
        HBox topBar = new HBox(10); // 10px spacing
        topBar.setPadding(new Insets(10));
        topBar.setStyle("-fx-background-color: #333;");
        topBar.setAlignment(Pos.CENTER);

        Label title = new Label("App Header");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
        topBar.getChildren().add(title);

        // ---- 2. LEFT: VBox (Vertical Layout) ----
        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(10));
        sidebar.setStyle("-fx-background-color: #ddd;");
        sidebar.setPrefWidth(150);

        sidebar.getChildren().addAll(
            new Button("Home"),
            new Button("Profile"),
            new Button("Settings")
        );

        // ---- 3. CENTER: GridPane (Grid Layout) ----
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setStyle("-fx-alignment: center;");

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button btn = new Button("Btn " + (row * 3 + col + 1));
                btn.setPrefSize(80, 40);
                grid.add(btn, col, row);
            }
        }

        // ---- 4. ASSEMBLE WITH BORDERPANE ----
        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setLeft(sidebar);
        root.setCenter(grid);

        Scene scene = new Scene(root, 700, 500);
        primaryStage.setTitle("JavaFX Layouts Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

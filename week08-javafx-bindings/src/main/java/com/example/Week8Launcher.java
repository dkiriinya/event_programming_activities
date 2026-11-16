package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Week 8 Master JavaFX Activity Launcher
 *
 * Provides a modern JavaFX dashboard to launch any or all Week 8
 * Properties and Bindings practical activities.
 */
public class Week8Launcher extends Application {

    private static class ActivityInfo {
        final String title;
        final String description;
        final Class<? extends Application> appClass;

        ActivityInfo(String title, String description, Class<? extends Application> appClass) {
            this.title = title;
            this.description = description;
            this.appClass = appClass;
        }
    }

    private final ActivityInfo[] activities = new ActivityInfo[] {
        new ActivityInfo(
            "1 & 2. Observable Properties & Listeners (Person + ListenerDemo)",
            "Demonstrates Person model (StringProperty & IntegerProperty) with InvalidationListener and ChangeListener.",
            ListenerDemo.class
        ),
        new ActivityInfo(
            "3. Unidirectional Binding (UnidirectionalDemo)",
            "Binds Label textProperty() unidirectionally to TextField textProperty().concat(\", welcome!\").",
            UnidirectionalDemo.class
        ),
        new ActivityInfo(
            "4. Bidirectional Binding (BidirectionalDemo)",
            "Two-way synchronization between Slider valueProperty() and TextField textProperty() via NumberStringConverter.",
            BidirectionalDemo.class
        ),
        new ActivityInfo(
            "5. Capstone: Reactive Registration Form (ReactiveFormApp)",
            "Live character counter, live bio preview, and declarative Submit button validation using Bindings.",
            ReactiveFormApp.class
        )
    };

    private Label statusLabel;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f8f9fa; -fx-padding: 24;");

        // Header
        VBox header = new VBox(6);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(0, 0, 16, 0));

        Label title = new Label("Week 8: JavaFX Properties & Bindings Dashboard");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #1a202c;");

        Label subtitle = new Label("Interactive Activity Launcher — Complete Self-Paced Showcase");
        subtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #718096;");

        header.getChildren().addAll(title, subtitle);
        root.setTop(header);

        // Cards list
        VBox cardContainer = new VBox(12);
        cardContainer.setAlignment(Pos.TOP_CENTER);
        cardContainer.setPadding(new Insets(8));

        for (ActivityInfo info : activities) {
            HBox card = createActivityCard(info);
            cardContainer.getChildren().add(card);
        }

        ScrollPane scrollPane = new ScrollPane(cardContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        root.setCenter(scrollPane);

        // Footer
        VBox footer = new VBox(10);
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(16, 0, 0, 0));

        Button launchAllBtn = new Button("▶  Launch All Activities Concurrently");
        launchAllBtn.setStyle(
            "-fx-background-color: #2b6cb0; -fx-text-fill: white; " +
            "-fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10 24; " +
            "-fx-background-radius: 6; -fx-cursor: hand;"
        );
        launchAllBtn.setOnAction(e -> launchAllActivities());

        statusLabel = new Label("Ready. Click any activity to launch.");
        statusLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #4a5568; -fx-font-style: italic;");

        footer.getChildren().addAll(launchAllBtn, statusLabel);
        root.setBottom(footer);

        Scene scene = new Scene(root, 660, 520);
        primaryStage.setTitle("Week 8: JavaFX Properties & Bindings Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox createActivityCard(ActivityInfo info) {
        HBox card = new HBox(16);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(14, 18, 14, 18));
        card.setStyle(
            "-fx-background-color: white; -fx-background-radius: 8; " +
            "-fx-border-color: #e2e8f0; -fx-border-radius: 8; -fx-border-width: 1;"
        );

        VBox textCol = new VBox(4);
        HBox.setHgrow(textCol, Priority.ALWAYS);

        Label name = new Label(info.title);
        name.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2d3748;");

        Label desc = new Label(info.description);
        desc.setWrapText(true);
        desc.setStyle("-fx-font-size: 12px; -fx-text-fill: #718096;");

        textCol.getChildren().addAll(name, desc);

        Button runBtn = new Button("Run  ▶");
        runBtn.setStyle(
            "-fx-background-color: #edf2f7; -fx-text-fill: #2b6cb0; " +
            "-fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 8 16; " +
            "-fx-background-radius: 6; -fx-cursor: hand;"
        );
        runBtn.setOnMouseEntered(e -> runBtn.setStyle(
            "-fx-background-color: #2b6cb0; -fx-text-fill: white; " +
            "-fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 8 16; " +
            "-fx-background-radius: 6; -fx-cursor: hand;"
        ));
        runBtn.setOnMouseExited(e -> runBtn.setStyle(
            "-fx-background-color: #edf2f7; -fx-text-fill: #2b6cb0; " +
            "-fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 8 16; " +
            "-fx-background-radius: 6; -fx-cursor: hand;"
        ));
        runBtn.setOnAction(e -> launchSingleActivity(info));

        card.getChildren().addAll(textCol, runBtn);
        return card;
    }

    private void launchSingleActivity(ActivityInfo info) {
        try {
            Stage newStage = new Stage();
            Application app = info.appClass.getDeclaredConstructor().newInstance();

            PrintStream originalOut = System.out;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream tee = new PrintStream(baos, true, StandardCharsets.UTF_8);
            System.setOut(new PrintStream(new java.io.OutputStream() {
                @Override
                public void write(int b) {
                    originalOut.write(b);
                    tee.write(b);
                }
            }, true, StandardCharsets.UTF_8));

            try {
                app.start(newStage);
            } finally {
                System.setOut(originalOut);
            }

            if (!newStage.isShowing()) {
                String consoleOutput = baos.toString(StandardCharsets.UTF_8);
                showConsoleOutputStage(newStage, info.title, consoleOutput);
            }
            statusLabel.setText("Launched: " + info.title);
        } catch (Exception ex) {
            statusLabel.setText("Error launching: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void showConsoleOutputStage(Stage stage, String title, String output) {
        VBox box = new VBox(10);
        box.setPadding(new Insets(16));
        box.setStyle("-fx-background-color: #1a202c;");

        Label header = new Label("Console Output — " + title);
        header.setStyle("-fx-text-fill: #e2e8f0; -fx-font-size: 13px; -fx-font-weight: bold;");

        TextArea area = new TextArea(output);
        area.setEditable(false);
        area.setStyle("-fx-control-inner-background: #2d3748; -fx-text-fill: #68d391; -fx-font-family: 'Consolas', monospace; -fx-font-size: 13px;");
        VBox.setVgrow(area, Priority.ALWAYS);

        box.getChildren().addAll(header, area);
        Scene scene = new Scene(box, 520, 300);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    private void launchAllActivities() {
        Alert alert = new Alert(
            Alert.AlertType.CONFIRMATION,
            "This will open all Week 8 activity windows concurrently. Continue?",
            ButtonType.YES, ButtonType.NO
        );
        alert.setTitle("Confirm Launch All");
        alert.setHeaderText("Launch All Week 8 Activities");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            for (ActivityInfo info : activities) {
                launchSingleActivity(info);
            }
            statusLabel.setText("All Week 8 activities launched concurrently!");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

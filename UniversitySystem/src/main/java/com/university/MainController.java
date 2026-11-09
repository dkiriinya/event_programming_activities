package com.university;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Controller for top-level navigation shell and dashboard content presentation.
 */
public class MainController {

    @FXML private StackPane centerPane;

    @FXML
    public void initialize() {
        showDashboard();
    }

    @FXML
    public void showDashboard() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 30;");

        // Header Title
        Label title = new Label("University Management System");
        title.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label subtitle = new Label("Event-Driven Architecture (EDA) with Neon PostgreSQL Cloud Persistence");
        subtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");

        // Stat Cards Container
        HBox cards = new HBox(16);
        cards.setAlignment(Pos.CENTER);
        cards.setMaxWidth(720);

        VBox card1 = createCard("☁️ Database Engine", "Neon PostgreSQL", "Serverless Lakebase (AWS us-east-2)\nSSL Mode: Require (Encrypted)", "#2980b9");
        VBox card2 = createCard("⚡ Architecture", "Event-Driven Concurrency", "Producer-Consumer JavaFX Tasks\nScheduled Heartbeat (every 5s)", "#27ae60");
        VBox card3 = createCard("👥 Student Directory", "100+ Active Students", "Full CRUD Operations\nLive Keystroke Search Filter", "#8e44ad");

        HBox.setHgrow(card1, Priority.ALWAYS);
        HBox.setHgrow(card2, Priority.ALWAYS);
        HBox.setHgrow(card3, Priority.ALWAYS);
        cards.getChildren().addAll(card1, card2, card3);

        // Action CTA Button
        Button openStudentsBtn = new Button("Open Student Directory & CRUD  ➔");
        openStudentsBtn.setStyle("-fx-background-color: #2c3e50; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 12 24; -fx-background-radius: 8; -fx-cursor: hand;");
        openStudentsBtn.setOnAction(e -> showStudents());

        // Footer info
        Label footer = new Label("Submitted by Don Gitonga Kiriinya | Event-Driven Programming in Java");
        footer.setStyle("-fx-font-size: 11px; -fx-text-fill: #95a5a6;");

        root.getChildren().addAll(title, subtitle, cards, openStudentsBtn, footer);
        centerPane.getChildren().setAll(root);
    }

    private VBox createCard(String iconTitle, String mainText, String description, String accentColor) {
        VBox card = new VBox(8);
        card.setStyle("-fx-background-color: white; -fx-padding: 18; -fx-background-radius: 10; -fx-border-color: #e2e8f0; -fx-border-radius: 10; -fx-border-width: 1.5; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.06), 10, 0, 0, 2);");

        Label cardHeader = new Label(iconTitle);
        cardHeader.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + accentColor + ";");

        Label cardMain = new Label(mainText);
        cardMain.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label cardDesc = new Label(description);
        cardDesc.setStyle("-fx-font-size: 11px; -fx-text-fill: #7f8c8d;");
        cardDesc.setWrapText(true);

        card.getChildren().addAll(cardHeader, cardMain, cardDesc);
        return card;
    }

    @FXML
    public void showStudents() {
        try {
            Parent studentView = FXMLLoader.load(getClass().getResource("StudentView.fxml"));
            centerPane.getChildren().setAll(studentView);
        } catch (IOException e) {
            e.printStackTrace();
            centerPane.getChildren().setAll(new Label("Error loading StudentView: " + e.getMessage()));
        }
    }
}

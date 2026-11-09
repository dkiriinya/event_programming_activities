package com.university;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Main Application Launcher with LO3 Scheduled Heartbeat Task.
 */
public class App extends Application {

    private ScheduledExecutorService scheduler;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize PostgreSQL tables and seeds if needed
        try {
            DatabaseUtil.initializeDatabase();
        } catch (Exception e) {
            System.err.println("Database initialization note: " + e.getMessage());
        }

        Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));

        primaryStage.setTitle("University Management System");
        primaryStage.setScene(new Scene(root, 1080, 680));
        primaryStage.setMinWidth(960);
        primaryStage.setMinHeight(600);
        primaryStage.show();

        // LO3: Scheduled background heartbeat task running every 5 seconds
        scheduler = Executors.newScheduledThreadPool(1);
        Runnable heartbeat = () -> {
            System.out.println("System Heartbeat: Checking for updates at " + new java.util.Date());
        };
        scheduler.scheduleAtFixedRate(heartbeat, 0, 5, TimeUnit.SECONDS);
    }

    @Override
    public void stop() throws Exception {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdownNow();
        }
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package programmaticdemo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Activity 4: FXML Application Launcher
 * Loads the login.fxml view, instantiates the LoginController, and initializes the Scene and Stage.
 */
public class FXMLApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();

        // Create the scene
        Scene scene = new Scene(root, 400, 350);
        primaryStage.setTitle("JavaFX Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

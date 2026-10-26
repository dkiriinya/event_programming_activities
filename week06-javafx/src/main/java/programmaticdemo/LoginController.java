package programmaticdemo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Activity 4: FXML Controller
 * Connects the visual FXML view to event-handling logic via @FXML injection and action handlers.
 */
public class LoginController {

    // The @FXML annotation injects the UI component from the FXML file
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;

    @FXML
    private void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Error: Fields cannot be empty.");
            return;
        }

        System.out.println("Attempting login for: " + username);

        // In a real app, you would check the database here
        if (username.equals("admin") && password.equals("password123")) {
            System.out.println("Login Successful!");
            usernameField.setText("");
            passwordField.setText("");
        } else {
            System.out.println("Login Failed!");
        }
    }
}

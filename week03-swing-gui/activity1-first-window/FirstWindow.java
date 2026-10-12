// ============================================================
// FIRST SWING WINDOW
// ============================================================
// This is the simplest possible Swing application.
// It creates a JFrame (main window) and displays it.
// ============================================================
import javax.swing.*;

public class FirstWindow {

    public static void main(String[] args) {
        // CRITICAL: All Swing code must run on the EDT.
        // This is the golden rule of Swing programming.
        SwingUtilities.invokeLater(() -> {
            createAndShowWindow();
        });
    }

    private static void createAndShowWindow() {
        // 1. Create the JFrame (the main window)
        JFrame frame = new JFrame("Don Gitonga");

        // 2. Set what happens when the user clicks the X button
        // EXIT_ON_CLOSE terminates the JVM when the window closes
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 3. Set the window's size in pixels (width x height)
        frame.setSize(800,600);

        // 4. Center the window on the screen
        frame.setLocation(100,100);

        // 5. Make the window visible (hidden by default)
        frame.setVisible(true);

        // 6. Set the window's title
        frame.setResizable(false);

        // Log to console to prove we're on the EDT
        System.out.println("Window created on thread: "
                + Thread.currentThread().getName());
    }
}

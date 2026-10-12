import javax.swing.*;
import java.awt.*;

public class FreezeDemo {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("EDT Demo — BAD Practice");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 200);
            frame.setLocationRelativeTo(null);
            frame.setLayout(new FlowLayout());

            JLabel status = new JLabel("Ready");
            JButton button = new JButton("Freeze the UI");

            // BAD: This blocks the EDT for 5 seconds!
            button.addActionListener(e -> {
                status.setText("Working...");
                try {
                    Thread.sleep(5000); // Simulates a long operation
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                status.setText("Done!");
            });

            frame.add(button);
            frame.add(status);
            frame.setVisible(true);
        });
    }
}

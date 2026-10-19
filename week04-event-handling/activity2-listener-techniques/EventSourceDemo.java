import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EventSourceDemo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Finding the Event Source");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 300);
            frame.setLocationRelativeTo(null);
            frame.setLayout(new GridLayout(4, 1, 10, 10));

            JLabel resultLabel = new JLabel("Click a button...", SwingConstants.CENTER);
            resultLabel.setFont(new Font("Arial", Font.BOLD, 16));

            JButton addButton = new JButton("Add");
            JButton subtractButton = new JButton("Subtract");
            JButton resetButton = new JButton("Reset");

            // Shared ActionListener for all buttons
            ActionListener sharedListener = e -> {
                Object source = e.getSource();
                String command = e.getActionCommand();

                if (source == addButton) {
                    resultLabel.setText("You clicked: ADD");
                } else if (source == subtractButton) {
                    resultLabel.setText("You clicked: SUBTRACT");
                } else if (source == resetButton) {
                    resultLabel.setText("You clicked: RESET");
                }
                System.out.println("Command: " + command);
            };

            addButton.addActionListener(sharedListener);
            subtractButton.addActionListener(sharedListener);
            resetButton.addActionListener(sharedListener);

            frame.add(addButton);
            frame.add(subtractButton);
            frame.add(resetButton);
            frame.add(resultLabel);
            frame.setVisible(true);
        });
    }
}

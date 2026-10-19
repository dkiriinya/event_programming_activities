import javax.swing.*;
import java.awt.*;

public class FirstButton {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(FirstButton::createAndShow);
    }

    private static void createAndShow() {
        JFrame frame = new JFrame("First Interactive Button");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 50));

        JButton clickButton = new JButton("Click Me!");

        // Add ActionListener using a lambda expression
        clickButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                frame,
                "You clicked the button!",
                "Button Clicked",
                JOptionPane.INFORMATION_MESSAGE
            );
        });

        frame.add(clickButton);
        frame.setVisible(true);
    }
}

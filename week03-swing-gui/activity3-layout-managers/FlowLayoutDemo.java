import javax.swing.*;
import java.awt.*;

public class FlowLayoutDemo {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("FlowLayout Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 300);
            frame.setLocationRelativeTo(null);

            // FlowLayout arranges components left-to-right,
            // wrapping to the next line when the row is full.
            frame.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

            for (int i = 1; i <= 12; i++) {
                frame.add(new JButton("Button " + i));
            }

            frame.setVisible(true);
        });
    }
}

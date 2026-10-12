import javax.swing.*;
import java.awt.*;

public class GridLayoutDemo {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("GridLayout — Calculator Style");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 500);
            frame.setLocationRelativeTo(null);

            // GridLayout: 4 rows, 4 columns, 5px gaps
            frame.setLayout(new GridLayout(4, 4, 5, 5));

            String[] labels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "C", "=", "+"
            };

            for (String label : labels) {
                frame.add(new JButton(label));
            }

            frame.setVisible(true);
        });
    }
}

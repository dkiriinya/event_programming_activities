import javax.swing.*;
import java.awt.*;

public class CombinedLayoutDemo {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Combined Layout — App Shell");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);

            // Main layout: BorderLayout
            frame.setLayout(new BorderLayout(5, 5));

            // 1. Toolbar (NORTH) — uses FlowLayout
            JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
            toolbar.add(new JButton("New"));
            toolbar.add(new JButton("Open"));
            toolbar.add(new JButton("Save"));
            toolbar.add(new JButton("Exit"));
            frame.add(toolbar, BorderLayout.NORTH);

            // 2. Sidebar (WEST) — uses GridLayout
            JPanel sidebar = new JPanel(new GridLayout(5, 1, 5, 5));
            sidebar.add(new JButton("Home"));
            sidebar.add(new JButton("Profile"));
            sidebar.add(new JButton("Messages"));
            sidebar.add(new JButton("Settings"));
            sidebar.add(new JButton("Logout"));
            frame.add(sidebar, BorderLayout.WEST);

            // 3. Main content (CENTER) — uses GridLayout for 3x3
            JPanel content = new JPanel(new GridLayout(3, 3, 10, 10));
            for (int i = 1; i <= 9; i++) {
                content.add(new JButton("Card " + i));
            }
            frame.add(content, BorderLayout.CENTER);

            // 4. Status bar (SOUTH) — a simple label
            JLabel statusBar = new JLabel("Ready");
            statusBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            frame.add(statusBar, BorderLayout.SOUTH);

            frame.setVisible(true);
        });
    }
}

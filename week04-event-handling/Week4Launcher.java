import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Week4Launcher extends JFrame {

    private static final String[][] ACTIVITIES = {
        {"1. First Interactive Button", "activity1-actionlistener-basics", "FirstButton", "Lambda ActionListener with popup dialog"},
        {"2.1 Listener Implementation Styles", "activity2-listener-techniques", "ListenerDemo", "Named, Inner, Lambda, and Method Reference"},
        {"2.2 Event Source Identification", "activity2-listener-techniques", "EventSourceDemo", "e.getSource() vs e.getActionCommand()"},
        {"3.1 Pizza Order Form", "activity3-item-and-document-listeners", "ItemListenerDemo", "ItemListener on Checkboxes, Radios & ComboBox"},
        {"3.2 Live Character Counter", "activity3-item-and-document-listeners", "DocumentListenerDemo", "DocumentListener real-time character count"},
        {"3.3 Live Search Filter", "activity3-item-and-document-listeners", "LiveSearchDemo", "Real-time list filtering with keystrokes"},
        {"4. Interactive Counter App", "activity4-counter-app", "CounterApp", "Step control, reset & keyboard shortcuts (Up/Down/R)"},
        {"5. Registration Form", "activity5-interactive-form", "RegistrationForm", "Multi-field validation, real-time error feedback"},
        {"6.1 Drawing Canvas", "activity6-mouse-and-key", "DrawingApp", "Mouse drag drawing with color picker & clear"},
        {"6.2 Keyboard Mover", "activity6-mouse-and-key", "KeyboardMover", "Move shapes with Arrow/WASD keys & bounds check"}
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Week4Launcher launcher = new Week4Launcher();
            launcher.setVisible(true);
        });
    }

    public Week4Launcher() {
        setTitle("Week 4: Event Handling Activity Launcher");
        setSize(700, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 20, 20));

        // Header
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 4, 4));
        JLabel titleLabel = new JLabel("Week 4: Interactive Swing Activities Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        JLabel subtitleLabel = new JLabel("Click any button below to launch an activity, or launch all simultaneously.", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitleLabel.setForeground(Color.DARK_GRAY);
        headerPanel.add(titleLabel);
        headerPanel.add(subtitleLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Grid of activity cards
        JPanel cardsPanel = new JPanel(new GridLayout(ACTIVITIES.length, 1, 8, 8));

        for (String[] act : ACTIVITIES) {
            String title = act[0];
            String folder = act[1];
            String className = act[2];
            String desc = act[3];

            JPanel rowPanel = new JPanel(new BorderLayout(10, 0));
            rowPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)
            ));

            JPanel textPanel = new JPanel(new GridLayout(2, 1, 2, 2));
            JLabel nameLbl = new JLabel(title);
            nameLbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
            JLabel descLbl = new JLabel(desc);
            descLbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            descLbl.setForeground(new Color(100, 100, 100));
            textPanel.add(nameLbl);
            textPanel.add(descLbl);

            JButton runBtn = new JButton("Run \u25B6");
            runBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
            runBtn.setPreferredSize(new Dimension(95, 32));
            runBtn.addActionListener(e -> launchActivity(folder, className));

            rowPanel.add(textPanel, BorderLayout.CENTER);
            rowPanel.add(runBtn, BorderLayout.EAST);
            cardsPanel.add(rowPanel);
        }

        JScrollPane scrollPane = new JScrollPane(cardsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom Controls
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton launchAllBtn = new JButton("\u25B6 Launch All 10 Activities Concurrently");
        launchAllBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        launchAllBtn.setBackground(new Color(33, 115, 70));
        launchAllBtn.setForeground(Color.WHITE);
        launchAllBtn.setFocusPainted(false);
        launchAllBtn.setPreferredSize(new Dimension(320, 40));
        launchAllBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "This will open 10 separate application windows simultaneously. Continue?",
                "Confirm Launch All",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                for (String[] act : ACTIVITIES) {
                    launchActivity(act[1], act[2]);
                }
            }
        });

        footerPanel.add(launchAllBtn);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private void launchActivity(String folder, String className) {
        new Thread(() -> {
            try {
                // Determine base directory
                File baseDir = new File("week04-event-handling/" + folder);
                if (!baseDir.exists()) {
                    baseDir = new File(folder); // in case running from inside week04-event-handling
                }

                ProcessBuilder pb = new ProcessBuilder("java", "-cp", baseDir.getAbsolutePath(), className);
                pb.inheritIO();
                pb.start();
            } catch (IOException ex) {
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                    this,
                    "Error launching " + className + ":\n" + ex.getMessage(),
                    "Execution Error",
                    JOptionPane.ERROR_MESSAGE
                ));
            }
        }).start();
    }
}

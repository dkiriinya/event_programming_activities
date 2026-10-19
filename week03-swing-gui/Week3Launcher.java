import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Week3Launcher extends JFrame {

    private static final String[][] ACTIVITIES = {
        {"1. First Swing Window", "activity1-first-window", "FirstWindow", "Centered basic JFrame on the EDT"},
        {"2. Component Showcase", "activity2-basic-components", "ComponentShowcase", "Labels, fields, radios, checkboxes & combobox"},
        {"3.1 FlowLayout Demo", "activity3-layout-managers", "FlowLayoutDemo", "Flow layout positioning and wrapping"},
        {"3.2 BorderLayout Demo", "activity3-layout-managers", "BorderLayoutDemo", "North, South, East, West, Center regions"},
        {"3.3 GridLayout Demo", "activity3-layout-managers", "GridLayoutDemo", "Calculator grid layout demonstration"},
        {"3.4 Combined Layouts Demo", "activity3-layout-managers", "CombinedLayoutDemo", "Nested layout managers for complex forms"},
        {"4.1 EDT Freeze Demo (Anti-Pattern)", "activity4-edt-rule", "FreezeDemo", "Simulates long-running task freezing UI"},
        {"4.2 EDT Fixed Demo (SwingWorker)", "activity4-edt-rule", "FixedDemo", "Thread-safe background worker keeping UI responsive"},
        {"5. Student Portal GUI Mockup", "activity5-complete-mockup", "StudentPortal", "Comprehensive dashboard with cards & sidebar"}
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Week3Launcher launcher = new Week3Launcher();
            launcher.setVisible(true);
        });
    }

    public Week3Launcher() {
        setTitle("Week 3: Swing GUI Activity Launcher");
        setSize(700, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 20, 20));

        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 4, 4));
        JLabel titleLabel = new JLabel("Week 3: Swing GUI Components & Layouts Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        JLabel subtitleLabel = new JLabel("Click any button below to launch an activity, or launch all simultaneously.", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitleLabel.setForeground(Color.DARK_GRAY);
        headerPanel.add(titleLabel);
        headerPanel.add(subtitleLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

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

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton launchAllBtn = new JButton("\u25B6 Launch All 9 Activities Concurrently");
        launchAllBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        launchAllBtn.setBackground(new Color(33, 115, 70));
        launchAllBtn.setForeground(Color.WHITE);
        launchAllBtn.setFocusPainted(false);
        launchAllBtn.setPreferredSize(new Dimension(300, 40));
        launchAllBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "This will open 9 separate application windows simultaneously. Continue?",
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
                File baseDir = new File("week03-swing-gui/" + folder);
                if (!baseDir.exists()) {
                    baseDir = new File(folder);
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

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Week2Launcher extends JFrame {

    private static final String[][] ACTIVITIES = {
        {"1. Weather Station Simulation", "activity1-weather-station", "WeatherStationMain", "Classic GoF Observer pattern with dynamic subscribe/unsubscribe"},
        {"2. Legacy java.util.Observer Demo", "activity2-legacy-observer", "LegacyMain", "Historical Observable/Observer classes (deprecated in Java 9)"},
        {"3a. Tightly Coupled Baseline", "activity3-custom-observer", "TightlyCoupledSystem", "Anti-pattern baseline where DataModel directly calls concrete UI"},
        {"3b. Refactored Custom Observer", "activity3-custom-observer", "RefactoredMain", "Decoupled DataSubject & DataObserver with multiple subscribers"},
        {"4. PropertyChangeListener Demo", "activity4-comparison", "PropertyChangeDemo", "Modern JavaBean event mechanism using PropertyChangeSupport"}
    };

    private JTextArea consoleArea;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Week2Launcher launcher = new Week2Launcher();
            launcher.setVisible(true);
        });
    }

    public Week2Launcher() {
        setTitle("Week 2: Observer Design Pattern Activity Launcher");
        setSize(820, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 12));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Header
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 4, 4));
        JLabel titleLabel = new JLabel("Week 2: Observer Design Pattern Control Center", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        JLabel subtitleLabel = new JLabel("Run observer simulations and inspect live console output directly below.", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitleLabel.setForeground(Color.DARK_GRAY);
        headerPanel.add(titleLabel);
        headerPanel.add(subtitleLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Center split: top has buttons, bottom has live output console
        JPanel cardsPanel = new JPanel(new GridLayout(ACTIVITIES.length, 1, 6, 6));

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
            runBtn.addActionListener(e -> runSimulation(folder, className, title));

            rowPanel.add(textPanel, BorderLayout.CENTER);
            rowPanel.add(runBtn, BorderLayout.EAST);
            cardsPanel.add(rowPanel);
        }

        // Live Console Panel
        JPanel consolePanel = new JPanel(new BorderLayout(5, 5));
        JLabel consoleHeader = new JLabel("Live Output Console:");
        consoleHeader.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JButton clearBtn = new JButton("Clear Console");
        clearBtn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        clearBtn.addActionListener(e -> consoleArea.setText(""));

        JPanel consoleTop = new JPanel(new BorderLayout());
        consoleTop.add(consoleHeader, BorderLayout.WEST);
        consoleTop.add(clearBtn, BorderLayout.EAST);

        consoleArea = new JTextArea();
        consoleArea.setEditable(false);
        consoleArea.setBackground(new Color(24, 24, 24));
        consoleArea.setForeground(new Color(220, 220, 220));
        consoleArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        consoleArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane consoleScroll = new JScrollPane(consoleArea);
        consoleScroll.setPreferredSize(new Dimension(780, 240));

        consolePanel.add(consoleTop, BorderLayout.NORTH);
        consolePanel.add(consoleScroll, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, cardsPanel, consolePanel);
        splitPane.setResizeWeight(0.5);
        splitPane.setDividerSize(6);
        mainPanel.add(splitPane, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 6));
        JButton runAllBtn = new JButton("\u25B6 Run All 5 Observer Demos Sequentially");
        runAllBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        runAllBtn.setBackground(new Color(33, 115, 70));
        runAllBtn.setForeground(Color.WHITE);
        runAllBtn.setFocusPainted(false);
        runAllBtn.setPreferredSize(new Dimension(320, 38));
        runAllBtn.addActionListener(e -> runAllSimulations());

        footerPanel.add(runAllBtn);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private void runSimulation(String folder, String className, String title) {
        new Thread(() -> {
            appendConsole("\n=======================================================\n");
            appendConsole(">>> RUNNING: " + title + " (" + className + ")\n");
            appendConsole("=======================================================\n");

            try {
                File baseDir = new File("Week02-observer-pattern/" + folder);
                if (!baseDir.exists()) {
                    baseDir = new File(folder);
                }

                ProcessBuilder pb = new ProcessBuilder("java", "-cp", baseDir.getAbsolutePath(), className);
                pb.redirectErrorStream(true);
                Process process = pb.start();

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        appendConsole(line + "\n");
                    }
                }
                process.waitFor();
                appendConsole("[Process finished with exit code " + process.exitValue() + "]\n");
            } catch (Exception ex) {
                appendConsole("Error: " + ex.getMessage() + "\n");
            }
        }).start();
    }

    private void runAllSimulations() {
        new Thread(() -> {
            consoleArea.setText("");
            appendConsole("Starting automated execution of all 5 Week 2 Observer activities...\n");
            for (String[] act : ACTIVITIES) {
                runSimulation(act[1], act[2], act[0]);
                try {
                    Thread.sleep(800);
                } catch (InterruptedException ignored) {}
            }
        }).start();
    }

    private void appendConsole(String text) {
        SwingUtilities.invokeLater(() -> {
            consoleArea.append(text);
            consoleArea.setCaretPosition(consoleArea.getDocument().getLength());
        });
    }
}

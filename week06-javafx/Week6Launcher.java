import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Master Activity Launcher for Week 6: Introduction to Modern JavaFX.
 * Provides a unified visual dashboard to launch any of the 5 JavaFX activities
 * individually or all concurrently in isolated processes.
 */
public class Week6Launcher extends JFrame {

    private static final String[][] ACTIVITIES = {
        {"1. Programmatic Hello World", "programmaticdemo.Main", "Stage, Scene, Node hierarchy & JavaFX Application Thread demo"},
        {"2. JavaFX Layout Panes", "programmaticdemo.LayoutDemo", "HBox top bar, VBox sidebar, GridPane 3x3, and BorderPane"},
        {"3. Visual FXML UI Design", "programmaticdemo.FXMLApp", "Scene Builder declarative login form (login.fxml)"},
        {"4. FXML Controller & Wiring", "programmaticdemo.FXMLApp", "@FXML component injection, event handler, and validation"},
        {"5. Capstone SaaS Dashboard", "programmaticdemo.DashboardApp", "Production SaaS layout with TopBar, Sidebar & 3 metric cards"}
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Week6Launcher launcher = new Week6Launcher();
            launcher.setVisible(true);
        });
    }

    public Week6Launcher() {
        setTitle("Week 6: Introduction to Modern JavaFX Activity Launcher");
        setSize(720, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 20, 20));

        // Header
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 4, 4));
        JLabel titleLabel = new JLabel("Week 6: Modern JavaFX Activities Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        JLabel subtitleLabel = new JLabel("Click any button below to launch a JavaFX activity, or launch all simultaneously.", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitleLabel.setForeground(Color.DARK_GRAY);
        headerPanel.add(titleLabel);
        headerPanel.add(subtitleLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Activity Cards
        JPanel cardsPanel = new JPanel(new GridLayout(ACTIVITIES.length, 1, 8, 8));

        for (String[] act : ACTIVITIES) {
            String title = act[0];
            String className = act[1];
            String desc = act[2];

            JPanel rowPanel = new JPanel(new BorderLayout(10, 0));
            rowPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
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
            runBtn.addActionListener(e -> launchActivity(className));

            rowPanel.add(textPanel, BorderLayout.CENTER);
            rowPanel.add(runBtn, BorderLayout.EAST);
            cardsPanel.add(rowPanel);
        }

        JScrollPane scrollPane = new JScrollPane(cardsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Footer with Launch All
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton launchAllBtn = new JButton("\u25B6 Launch All Activities Concurrently");
        launchAllBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        launchAllBtn.setBackground(new Color(33, 115, 70));
        launchAllBtn.setForeground(Color.WHITE);
        launchAllBtn.setFocusPainted(false);
        launchAllBtn.setPreferredSize(new Dimension(320, 40));
        launchAllBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "This will open separate JavaFX application windows simultaneously. Continue?",
                "Confirm Launch All",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                // Launch unique classes (Main, LayoutDemo, FXMLApp, DashboardApp)
                String[] distinctClasses = {
                    "programmaticdemo.Main",
                    "programmaticdemo.LayoutDemo",
                    "programmaticdemo.FXMLApp",
                    "programmaticdemo.DashboardApp"
                };
                for (String cls : distinctClasses) {
                    launchActivity(cls);
                }
            }
        });

        footerPanel.add(launchAllBtn);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private void launchActivity(String className) {
        new Thread(() -> {
            try {
                boolean isWindows = System.getProperty("os.name", "").toLowerCase().contains("win");
                File rootDir = new File(".").getAbsoluteFile();
                File week06Dir = new File("week06-javafx");
                if (!week06Dir.exists()) {
                    week06Dir = new File(".");
                }

                File classesDir = new File(week06Dir, "target/classes");
                if (!classesDir.exists()) {
                    classesDir = new File("target/classes");
                }

                File libDir = new File(week06Dir, isWindows ? "lib/win" : "lib/linux");
                if (!libDir.exists()) {
                    libDir = new File(isWindows ? "lib/win" : "lib/linux");
                }

                List<String> command = new ArrayList<>();
                if (libDir.exists() && classesDir.exists()) {
                    command.add("java");
                    command.add("--module-path");
                    command.add(libDir.getAbsolutePath());
                    command.add("--add-modules");
                    command.add("javafx.controls,javafx.fxml");
                    command.add("-cp");
                    command.add(classesDir.getAbsolutePath());
                    command.add(className);
                } else {
                    // Fallback to Maven / Maven Wrapper
                    String mvnCmd = isWindows ? "mvnw.cmd" : "./mvnw";
                    File mvnFile = new File(week06Dir, mvnCmd);
                    command.add(mvnFile.exists() ? mvnFile.getAbsolutePath() : "mvn");
                    command.add("javafx:run");
                    command.add("-Djavafx.mainClass=" + className);
                }

                ProcessBuilder pb = new ProcessBuilder(command);
                pb.directory(week06Dir.exists() ? week06Dir : rootDir);
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

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

public class FileExplorerDemo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("File Explorer Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 500);
            frame.setLocationRelativeTo(null);

            // PART 1: JTree
            DefaultMutableTreeNode root = new DefaultMutableTreeNode("My Computer");
            DefaultMutableTreeNode documents = new DefaultMutableTreeNode("Documents");
            documents.add(new DefaultMutableTreeNode("Resume.docx"));
            documents.add(new DefaultMutableTreeNode("CV.pdf"));
            documents.add(new DefaultMutableTreeNode("Notes.txt"));

            DefaultMutableTreeNode pictures = new DefaultMutableTreeNode("Pictures");
            pictures.add(new DefaultMutableTreeNode("Vacation.jpg"));
            pictures.add(new DefaultMutableTreeNode("Family.jpg"));

            DefaultMutableTreeNode projects = new DefaultMutableTreeNode("Projects");
            DefaultMutableTreeNode javaProject = new DefaultMutableTreeNode("JavaApp");
            javaProject.add(new DefaultMutableTreeNode("Main.java"));
            javaProject.add(new DefaultMutableTreeNode("Utils.java"));
            projects.add(javaProject);

            root.add(documents);
            root.add(pictures);
            root.add(projects);

            JTree tree = new JTree(new DefaultTreeModel(root));
            tree.setFont(new Font("Arial", Font.PLAIN, 14));
            tree.setRowHeight(25);

            JTextArea infoArea = new JTextArea(3, 30);
            infoArea.setEditable(false);
            tree.addTreeSelectionListener(e -> {
                DefaultMutableTreeNode selected =
                        (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (selected != null) {
                    infoArea.setText("Selected: " + selected.getUserObject() + "\nLevel: " + selected.getLevel());
                }
            });

            // PART 2: JTabbedPane
            JTabbedPane tabs = new JTabbedPane();

            JPanel generalTab = new JPanel();
            generalTab.setLayout(new BoxLayout(generalTab, BoxLayout.Y_AXIS));
            generalTab.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            generalTab.add(new JLabel("General Settings"));
            generalTab.add(Box.createVerticalStrut(10));
            generalTab.add(new JCheckBox("Show hidden files"));
            generalTab.add(new JCheckBox("Auto-save every 5 minutes"));

            JPanel appearanceTab = new JPanel();
            appearanceTab.setLayout(new BoxLayout(appearanceTab, BoxLayout.Y_AXIS));
            appearanceTab.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            appearanceTab.add(new JLabel("Theme Selection:"));
            appearanceTab.add(Box.createVerticalStrut(10));
            appearanceTab.add(new JRadioButton("Light Theme", true));
            appearanceTab.add(new JRadioButton("Dark Theme"));

            tabs.addTab("General", generalTab);
            tabs.addTab("Appearance", appearanceTab);

            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(tree), tabs);
            splitPane.setDividerLocation(250);

            frame.add(splitPane, BorderLayout.CENTER);
            frame.add(new JScrollPane(infoArea), BorderLayout.SOUTH);
            frame.setVisible(true);
        });
    }
}

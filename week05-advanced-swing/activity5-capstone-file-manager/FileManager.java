import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;

public class FileManager {
    private JFrame frame;
    private JTree folderTree;
    private JTable fileTable;
    private DefaultTableModel tableModel;
    private JLabel statusBar;
    private File currentFolder;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FileManager().createAndShow());
    }

    private void createAndShow() {
        frame = new JFrame("File Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(950, 620);
        frame.setLocationRelativeTo(null);

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem refreshItem = new JMenuItem("Refresh");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(refreshItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        helpMenu.add(aboutItem);
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        frame.setJMenuBar(menuBar);

        // Toolbar
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        JButton homeBtn = new JButton("Home");
        JButton upBtn = new JButton("Up Folder");
        JButton refreshBtn = new JButton("Refresh");
        toolbar.add(homeBtn);
        toolbar.add(upBtn);
        toolbar.addSeparator();
        toolbar.add(refreshBtn);
        frame.add(toolbar, BorderLayout.NORTH);

        // Left Tree (depth-bounded for fast, non-blocking initial loading)
        File homeDir = new File(System.getProperty("user.home"));
        DefaultMutableTreeNode root = buildFolderTree(homeDir);
        folderTree = new JTree(new DefaultTreeModel(root));
        folderTree.setFont(new Font("Arial", Font.PLAIN, 14));
        folderTree.setRowHeight(22);
        folderTree.expandRow(0);

        // Custom renderer so directories show clean names instead of full absolute paths
        folderTree.setCellRenderer(new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value,
                    boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
                if (value instanceof DefaultMutableTreeNode) {
                    Object uo = ((DefaultMutableTreeNode) value).getUserObject();
                    if (uo instanceof File) {
                        File f = (File) uo;
                        setText(f.getName().isEmpty() ? f.getPath() : f.getName());
                    }
                }
                return this;
            }
        });

        folderTree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode selected = (DefaultMutableTreeNode) folderTree.getLastSelectedPathComponent();
            if (selected != null && selected.getUserObject() instanceof File) {
                File folder = (File) selected.getUserObject();
                if (folder.isDirectory()) {
                    loadFolderContents(folder);
                }
            }
        });

        // Right Table
        String[] columns = {"Name", "Type", "Size", "Modified"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        fileTable = new JTable(tableModel);
        fileTable.setRowHeight(24);
        fileTable.setFont(new Font("Arial", Font.PLAIN, 13));
        fileTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        fileTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Right align file size column
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        fileTable.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(folderTree), new JScrollPane(fileTable));
        splitPane.setDividerLocation(280);
        frame.add(splitPane, BorderLayout.CENTER);

        // Status Bar
        statusBar = new JLabel("Ready");
        statusBar.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        frame.add(statusBar, BorderLayout.SOUTH);

        // Initial Load
        loadFolderContents(homeDir);

        homeBtn.addActionListener(e -> loadFolderContents(homeDir));
        upBtn.addActionListener(e -> {
            if (currentFolder != null && currentFolder.getParentFile() != null) {
                loadFolderContents(currentFolder.getParentFile());
            }
        });
        refreshBtn.addActionListener(e -> {
            if (currentFolder != null) loadFolderContents(currentFolder);
        });

        refreshItem.addActionListener(e -> refreshBtn.doClick());
        exitItem.addActionListener(e -> System.exit(0));
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(frame,
                "File Manager v1.0\nBuilt with Java Swing Capstone", "About", JOptionPane.INFORMATION_MESSAGE));

        // Double-click folder navigation
        fileTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = fileTable.getSelectedRow();
                    if (row >= 0) {
                        String fileName = (String) tableModel.getValueAt(row, 0);
                        File file = new File(currentFolder, fileName);
                        if (file.isDirectory()) {
                            loadFolderContents(file);
                        } else {
                            JOptionPane.showMessageDialog(frame,
                                    "Selected file: " + file.getAbsolutePath() + "\nSize: " + formatSize(file.length()),
                                    "File Info", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
        });

        frame.setVisible(true);
    }

    private DefaultMutableTreeNode buildFolderTree(File folder) {
        return buildFolderTree(folder, 0, 2);
    }

    private DefaultMutableTreeNode buildFolderTree(File folder, int currentDepth, int maxDepth) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(folder);
        if (currentDepth < maxDepth) {
            File[] children = folder.listFiles();
            if (children != null) {
                for (File child : children) {
                    if (child.isDirectory() && !child.isHidden()) {
                        node.add(buildFolderTree(child, currentDepth + 1, maxDepth));
                    }
                }
            }
        }
        return node;
    }

    private void loadFolderContents(File folder) {
        if (!folder.isDirectory()) return;
        currentFolder = folder;
        tableModel.setRowCount(0);

        File[] files = folder.listFiles();
        if (files != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, HH:mm");
            for (File file : files) {
                String type = file.isDirectory() ? "Folder" : getExtension(file.getName());
                String size = file.isDirectory() ? "—" : formatSize(file.length());
                String modified = sdf.format(file.lastModified());
                tableModel.addRow(new Object[]{file.getName(), type, size, modified});
            }
        }
        frame.setTitle("File Manager — " + folder.getAbsolutePath());
        statusBar.setText(files != null ? files.length + " items in " + folder.getName() : "Cannot read folder");
    }

    private String getExtension(String name) {
        int dot = name.lastIndexOf('.');
        return dot >= 0 ? name.substring(dot + 1).toUpperCase() : "File";
    }

    private String formatSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format("%.1f KB", bytes / 1024.0);
        return String.format("%.1f MB", bytes / (1024.0 * 1024.0));
    }
}

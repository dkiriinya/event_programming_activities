# Week 5: Advanced Swing Components & Multi-Pane Applications

## Overview & Learning Outcomes

During Week 5, the focus transitions from basic form-building to enterprise-level desktop application development in Java Swing. You learn to handle complex, data-heavy interfaces using advanced components (lists, tables, trees, tabbed panes, and split panes), custom cell renderers, menus, toolbars, file choosers, and dialogs.

---

### Core Learning Outcomes Summary

| Learning Outcome | Description | Key Swing Components & Classes | Real-World Applications |
| :--- | :--- | :--- | :--- |
| **LO1: Advanced Components** | Create and use lists, tables, trees, and tabbed navigation. | `JList`, `JTable`, `JTree`, `JTabbedPane`, `JScrollPane` | Excel, Spotify playlists, Windows Explorer sidebar, Chrome tabs |
| **LO2: Custom Renderers** | Implement custom cell renderers for displaying styled/complex data. | `setCellRenderer()`, `DefaultListCellRenderer`, `DefaultTableCellRenderer` | WhatsApp avatars/badges, stock market color coding, priority alerts |
| **LO3: Menus, Toolbars & Dialogs** | Create application menu bars, toolbars, OS-standard file choosers, and popup alerts. | `JMenuBar`, `JMenu`, `JMenuItem`, `JToolBar`, `JOptionPane`, `JFileChooser` | Notepad, Microsoft Word, IDE navigation bars |
| **LO4: Multi-Pane Architecture** | Build multi-window/multi-pane applications with split layouts and event coordination. | `JSplitPane`, `TreeSelectionListener`, `DefaultTableModel` | Windows File Explorer, VS Code, IDE project panels, FileZilla |

---

## Detailed Learning Outcomes Breakdown

### LO1: Create and Use Advanced Swing Components
- **`JList`**: Displays a scrollable list of selectable items.
- **`JTable`**: Displays and edits multi-column tabular data using a `TableModel`.
- **`JTree`**: Displays hierarchical, folder-like structures using parent/child nodes.
- **`JTabbedPane`**: Groups related controls into multiple clickable tab views to conserve screen area.
- **`JScrollPane`**: Mandatory scroll wrapper for `JList`, `JTable`, and `JTree` to display headers and scrollbars.

### LO2: Custom Renderers
- **Default Behavior**: `JList` and `JTable` render plain text via `toString()`.
- **Custom Cell Rendering**: Overriding `getListCellRendererComponent()` or `getTableCellRendererComponent()` allows conditional styling (e.g., highlighting GPAs $< 3.0$ in red, adding status dot icons).
- **Performance**: Swing reuses cell renderer instances across visible rows, keeping memory overhead minimal.

### LO3: Create and Manage Dialogs, Menus, and Toolbars
- **Menu Hierarchy**: `JMenuBar` $ightarrow$ `JMenu` $ightarrow$ `JMenuItem`.
- **`JToolBar`**: Holds quick-action buttons and status components.
- **`JOptionPane`**: Provides modal popups (`showMessageDialog`, `showConfirmDialog`, `showInputDialog`).
- **`JFileChooser`**: Provides OS-native Open File and Save As file pickers with `FileNameExtensionFilter`.

### LO4: Multi-Window / Multi-Pane Applications
- **`JSplitPane`**: Divides viewports horizontally or vertically with an adjustable divider.
- **Event Coordination**: Linking tree selection events (`TreeSelectionListener`) to table models (`DefaultTableModel.setRowCount(0)` and `addRow()`).

---

## Setup & Project Directory Structure

```text
week05-advanced-swing/
├── activity1-jlist-renderer/
│   └── ContactListDemo.java
├── activity2-jtable/
│   └── StudentTableDemo.java
├── activity3-jtree-tabs/
│   └── FileExplorerDemo.java
├── activity4-menus-dialogs/
│   └── MiniEditor.java
└── activity5-capstone-file-manager/
    └── FileManager.java
```

---

## Activity 1: JList and Custom Renderers

### Purpose
Build a contact list using `JList` and a custom `ListCellRenderer` that attaches status icons (Green for Online, Orange for Away, Gray for Offline).

### Code Implementation (`ContactListDemo.java`)

```java
package activity1;

import javax.swing.*;
import java.awt.*;

public class ContactListDemo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Contact List — Custom Renderer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.setLocationRelativeTo(null);

            String[] contacts = {
                "Alice — Online",
                "Bob — Offline",
                "Charlie — Online",
                "Diana — Away",
                "Edward — Offline"
            };

            JList<String> contactList = new JList<>(contacts);
            contactList.setFont(new Font("Arial", Font.PLAIN, 14));

            // Custom Renderer
            contactList.setCellRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(
                        JList<?> list, Object value, int index,
                        boolean isSelected, boolean cellHasFocus) {
                    
                    JLabel label = (JLabel) super.getListCellRendererComponent(
                            list, value, index, isSelected, cellHasFocus);
                    
                    String text = value.toString();
                    if (text.contains("Online")) {
                        label.setIcon(new ColorDot(Color.GREEN));
                    } else if (text.contains("Away")) {
                        label.setIcon(new ColorDot(Color.ORANGE));
                    } else {
                        label.setIcon(new ColorDot(Color.GRAY));
                    }
                    
                    label.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
                    return label;
                }
            });

            JTextArea info = new JTextArea(3, 30);
            info.setEditable(false);
            contactList.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    info.setText("Selected: " + contactList.getSelectedValue());
                }
            });

            frame.add(new JScrollPane(contactList), BorderLayout.CENTER);
            frame.add(new JScrollPane(info), BorderLayout.SOUTH);
            frame.setVisible(true);
        });
    }

    static class ColorDot implements Icon {
        private final Color color;
        ColorDot(Color color) {
            this.color = color;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(color);
            g.fillOval(x, y, 12, 12);
        }

        @Override public int getIconWidth() { return 14; }
        @Override public int getIconHeight() { return 14; }
    }
}
```

### Self-Check Questions & Troubleshooting
- **Why use `!e.getValueIsAdjusting()`?** Prevents duplicate listener execution while mouse clicks or keyboard navigation are in flux.
- **Troubleshooting**: If items appear squished, add an `EmptyBorder` to the renderer label.

---

## Activity 2: JTable — Tabular Data Display

### Purpose
Implement a student database table using `JTable` and `DefaultTableModel` with row insertion, row deletion, column-level editing constraints, and custom cell rendering for GPA values.

### Code Implementation (`StudentTableDemo.java`)

```java
package activity2;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudentTableDemo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Student Database — JTable");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 500);
            frame.setLocationRelativeTo(null);

            String[] columns = {"ID", "Name", "Age", "Grade", "GPA"};
            Object[][] data = {
                {101, "Alice Johnson", 20, "A", 3.85},
                {102, "Bob Smith", 22, "B", 3.20},
                {103, "Charlie Brown", 19, "A-", 3.70},
                {104, "Diana Prince", 21, "B+", 3.40},
                {105, "Edward Norton", 23, "C", 2.50},
                {106, "Fiona Green", 20, "A", 3.95}
            };

            DefaultTableModel model = new DefaultTableModel(data, columns) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    // Only Name (col 1) and Grade (col 3) are editable
                    return column == 1 || column == 3;
                }
            };

            JTable table = new JTable(model);
            table.setRowHeight(25);
            table.setFont(new Font("Arial", Font.PLAIN, 14));
            table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            // Custom Renderer for GPA column (index 4)
            table.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(
                        JTable table, Object value, boolean isSelected,
                        boolean hasFocus, int row, int column) {
                    
                    Component c = super.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column);
                    
                    if (!isSelected && value instanceof Double) {
                        double gpa = (Double) value;
                        if (gpa < 3.0) {
                            c.setForeground(Color.RED);
                        } else {
                            c.setForeground(Color.BLACK);
                        }
                    }
                    return c;
                }
            });

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            JButton addButton = new JButton("Add Student");
            JButton deleteButton = new JButton("Delete Selected");
            JButton clearButton = new JButton("Clear All");

            addButton.addActionListener(e -> {
                String name = JOptionPane.showInputDialog(frame, "Enter student name:");
                if (name == null || name.trim().isEmpty()) return;
                String ageStr = JOptionPane.showInputDialog(frame, "Enter age:");
                if (ageStr == null) return;
                String grade = JOptionPane.showInputDialog(frame, "Enter grade:");
                if (grade == null) return;
                String gpaStr = JOptionPane.showInputDialog(frame, "Enter GPA:");
                if (gpaStr == null) return;

                try {
                    int newId = model.getRowCount() + 101;
                    int age = Integer.parseInt(ageStr);
                    double gpa = Double.parseDouble(gpaStr);
                    model.addRow(new Object[]{newId, name, age, grade, gpa});
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Age and GPA must be valid numbers",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            deleteButton.addActionListener(e -> {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(frame, "Please select a row to delete",
                            "No Selection", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(frame, "Delete selected student?",
                        "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    model.removeRow(selectedRow);
                }
            });

            clearButton.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(frame, "Clear ALL students?",
                        "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    model.setRowCount(0);
                }
            });

            buttonPanel.add(addButton);
            buttonPanel.add(deleteButton);
            buttonPanel.add(clearButton);

            frame.add(new JScrollPane(table), BorderLayout.CENTER);
            frame.add(buttonPanel, BorderLayout.SOUTH);
            frame.setVisible(true);
        });
    }
}
```

---

## Activity 3: JTree and JTabbedPane

### Purpose
Combine hierarchical tree views (`JTree`) with tabbed content layouts (`JTabbedPane`) in a resizable `JSplitPane`.

### Code Implementation (`FileExplorerDemo.java`)

```java
package activity3;

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
                    infoArea.setText("Selected: " + selected.getUserObject() + "
Level: " + selected.getLevel());
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
```

---

## Activity 4: Menus, Toolbars, and Dialogs

### Purpose
Build a mini text editor (`MiniEditor`) equipped with menu navigation (`JMenuBar`), toolbar shortcuts (`JToolBar`), status feedback, real-time document word counting, and native file selection (`JFileChooser`).

### Code Implementation (`MiniEditor.java`)

```java
package activity4;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MiniEditor {
    private JFrame frame;
    private JTextArea textArea;
    private File currentFile;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MiniEditor().createAndShow());
    }

    private void createAndShow() {
        frame = new JFrame("Mini Editor — Untitled");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem openItem = new JMenuItem("Open...");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem saveAsItem = new JMenuItem("Save As...");
        JMenuItem exitItem = new JMenuItem("Exit");

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        JMenu editMenu = new JMenu("Edit");
        JMenuItem cutItem = new JMenuItem("Cut");
        JMenuItem copyItem = new JMenuItem("Copy");
        JMenuItem pasteItem = new JMenuItem("Paste");
        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);
        frame.setJMenuBar(menuBar);

        // Toolbar
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        JButton newBtn = new JButton("New");
        JButton openBtn = new JButton("Open");
        JButton saveBtn = new JButton("Save");
        JLabel wordCountLabel = new JLabel("0");

        toolbar.add(newBtn);
        toolbar.add(openBtn);
        toolbar.add(saveBtn);
        toolbar.addSeparator();
        toolbar.add(new JLabel("  Words: "));
        toolbar.add(wordCountLabel);
        frame.add(toolbar, BorderLayout.NORTH);

        // Text Area
        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);

        JLabel statusBar = new JLabel("Ready");
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        frame.add(statusBar, BorderLayout.SOUTH);

        // Handlers
        Runnable newAction = () -> {
            textArea.setText("");
            currentFile = null;
            frame.setTitle("Mini Editor — Untitled");
            statusBar.setText("New file created");
        };

        newItem.addActionListener(e -> newAction.run());
        newBtn.addActionListener(e -> newAction.run());

        Runnable openAction = () -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
            int result = chooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                currentFile = chooser.getSelectedFile();
                try {
                    String content = Files.readString(currentFile.toPath());
                    textArea.setText(content);
                    frame.setTitle("Mini Editor — " + currentFile.getName());
                    statusBar.setText("Opened: " + currentFile.getName());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error opening file: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        openItem.addActionListener(e -> openAction.run());
        openBtn.addActionListener(e -> openAction.run());

        Runnable saveAsAction = () -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
            int result = chooser.showSaveDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                currentFile = chooser.getSelectedFile();
                try {
                    Files.writeString(currentFile.toPath(), textArea.getText());
                    frame.setTitle("Mini Editor — " + currentFile.getName());
                    statusBar.setText("Saved as: " + currentFile.getName());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error saving: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        saveAsItem.addActionListener(e -> saveAsAction.run());

        Runnable saveAction = () -> {
            if (currentFile == null) {
                saveAsAction.run();
            } else {
                try {
                    Files.writeString(currentFile.toPath(), textArea.getText());
                    statusBar.setText("Saved: " + currentFile.getName());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error saving: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        saveItem.addActionListener(e -> saveAction.run());
        saveBtn.addActionListener(e -> saveAction.run());

        exitItem.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(frame, "Exit without saving?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) System.exit(0);
        });

        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(frame,
                "Mini Editor v1.0\nBuilt with Java Swing", "About", JOptionPane.INFORMATION_MESSAGE));

        cutItem.addActionListener(e -> textArea.cut());
        copyItem.addActionListener(e -> textArea.copy());
        pasteItem.addActionListener(e -> textArea.paste());

        // Live Word Count
        textArea.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override public void insertUpdate(javax.swing.event.DocumentEvent e) { updateCount(); }
            @Override public void removeUpdate(javax.swing.event.DocumentEvent e) { updateCount(); }
            @Override public void changedUpdate(javax.swing.event.DocumentEvent e) { }

            private void updateCount() {
                String text = textArea.getText().trim();
                int words = text.isEmpty() ? 0 : text.split("\\s+").length;
                wordCountLabel.setText(String.valueOf(words));
            }
        });

        frame.setVisible(true);
    }
}
```

---

## Activity 5: Capstone — Interactive File Manager

### Purpose
Integrate all learned components into a full-fledged File Manager desktop application (`FileManager.java`) featuring folder navigation (`JTree`), real-time file listing (`JTable`), split pane layout (`JSplitPane`), action toolbar (`JToolBar`), and file system double-click execution handlers.

### Code Implementation (`FileManager.java`)

```java
package activity5;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
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
        frame.setSize(900, 600);
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

        // Left Tree
        File homeDir = new File(System.getProperty("user.home"));
        DefaultMutableTreeNode root = buildFolderTree(homeDir);
        folderTree = new JTree(new DefaultTreeModel(root));
        folderTree.setFont(new Font("Arial", Font.PLAIN, 14));
        folderTree.setRowHeight(22);
        folderTree.expandRow(0);

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
        fileTable.setRowHeight(22);
        fileTable.setFont(new Font("Arial", Font.PLAIN, 13));
        fileTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(folderTree), new JScrollPane(fileTable));
        splitPane.setDividerLocation(280);
        frame.add(splitPane, BorderLayout.CENTER);

        // Status Bar
        statusBar = new JLabel("Ready");
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
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
                            JOptionPane.showMessageDialog(frame, "Selected file: " + file.getAbsolutePath(),
                                    "File Info", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
        });

        frame.setVisible(true);
    }

    private DefaultMutableTreeNode buildFolderTree(File folder) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(folder);
        File[] children = folder.listFiles();
        if (children != null) {
            for (File child : children) {
                if (child.isDirectory() && !child.isHidden()) {
                    node.add(buildFolderTree(child));
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
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
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
```

---

## Self-Assessment Checklist & Reflection

Before advancing to Week 6 (JavaFX), verify that you can perform all of the following:

- [x] Populate a `JTable` dynamically using `DefaultTableModel` (`addRow`, `removeRow`, `setRowCount(0)`).
- [x] Determine appropriate use cases for `JList` vs. `JTable` vs. `JTree`.
- [x] Construct node hierarchies with `DefaultMutableTreeNode` and `DefaultTreeModel`.
- [x] Configure multi-view tab interfaces using `JTabbedPane`.
- [x] Author custom renderers (`getListCellRendererComponent`, `getTableCellRendererComponent`) for color-coding and icons.
- [x] Construct native desktop menu bars (`JMenuBar`), toolbars (`JToolBar`), file choosers (`JFileChooser`), and dialogs (`JOptionPane`).
- [x] Assemble multi-pane split layouts using `JSplitPane`.

---

## Git & GitHub Submission Flow

To save and push your Week 5 implementations to your remote repository:

```powershell
cd "C:\Users\RUICT\Desktop\projects\event-driven-programming-java"
git status
git add .
git commit -m "Complete Week 5: Advanced Swing Components & File Manager Capstone"
git push origin main
```

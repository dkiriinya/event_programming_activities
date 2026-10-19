# Week 5 — Advanced Swing Components & Multi-Pane Applications: Self-Assessment & Activity Report

---

## Executive Summary & Deliverables Checklist

- [x] **Activity 1: JList and Custom Renderers** — [`activity1-jlist-renderer/ContactListDemo.java`](activity1-jlist-renderer/ContactListDemo.java)
  - Configured `JList` with custom `DefaultListCellRenderer`.
  - Dynamically styled online, away, and offline contacts using custom `Icon` badges (`ColorDot`).
  - Added selection listener with `!e.getValueIsAdjusting()` check for status display.
- [x] **Activity 2: JTable and Tabular Data Operations** — [`activity2-jtable/StudentTableDemo.java`](activity2-jtable/StudentTableDemo.java)
  - Constructed `DefaultTableModel` with selective cell editability (`isCellEditable`).
  - Implemented custom `DefaultTableCellRenderer` for conditional GPA color highlighting (< 3.0 in red).
  - Wired interactive CRUD actions: dynamic row insertion, row deletion with validation, and bulk clearance.
- [x] **Activity 3: Hierarchical Trees and Tabbed Interfaces** — [`activity3-jtree-tabs/FileExplorerDemo.java`](activity3-jtree-tabs/FileExplorerDemo.java)
  - Constructed multi-level directory tree using `DefaultMutableTreeNode` and `DefaultTreeModel`.
  - Built tabbed navigation pane (`JTabbedPane`) for General and Appearance configuration panels.
  - Linked components inside a split view (`JSplitPane`) with selection listener tracking node path and depth level.
- [x] **Activity 4: Menus, Toolbars, and Native File Dialogs** — [`activity4-menus-dialogs/MiniEditor.java`](activity4-menus-dialogs/MiniEditor.java)
  - Implemented application menu bar hierarchy (`JMenuBar`, `JMenu`, `JMenuItem`) with File, Edit, and Help menus.
  - Created non-floatable action `JToolBar` with file shortcut buttons and a live document word counter (`DocumentListener`).
  - Integrated `JFileChooser` with `FileNameExtensionFilter` for Open and Save/Save As operations.
- [x] **Activity 5: Interactive File Manager Capstone** — [`activity5-capstone-file-manager/FileManager.java`](activity5-capstone-file-manager/FileManager.java)
  - Full multi-pane file browser coordinating `JTree` directory hierarchy with `JTable` file details inside `JSplitPane`.
  - Added home navigation, parent directory traversal ("Up Folder"), refresh actions, and double-click directory descent.
  - Bounded tree generation to ensure non-blocking, responsive EDT startup.
- [x] **Clean Compilation & Thread Safety** — All Java sources run strictly on the Event Dispatch Thread (EDT) via `SwingUtilities.invokeLater()` and compile with zero errors.

---

## Learning Outcomes Self-Assessment

### Learning Outcome 1: Create and Use Advanced Swing Components
- **Self-Rating:** 5 / 5
- **Advanced Components Comparison Matrix:**

| Component | Underlying Data Model | Primary Data Structure | Common Real-World Use Case |
| :--- | :--- | :--- | :--- |
| **`JList<E>`** | `ListModel<E>` / `DefaultListModel<E>` | Linear, 1D array or collection | Message contact lists, option selectors, playlist queues. |
| **`JTable`** | `TableModel` / `DefaultTableModel` | 2D tabular grid (rows and columns) | Spreadsheets, database viewers, order inventories. |
| **`JTree`** | `TreeModel` / `DefaultTreeModel` | Hierarchical N-ary tree (`TreeNode`) | Project folder sidebars, taxonomy browsers, DOM inspectors. |
| **`JTabbedPane`** | Tab index / Page model | Stacked container views | Multi-document editors, settings/preferences dialogs. |
| **`JSplitPane`** | Split configuration (horiz/vert) | Dual component viewports | Master-detail layouts, IDE workspaces, side-by-side diff viewers. |

---

### Learning Outcome 2: Custom Renderers and Visual Styling
- **Self-Rating:** 5 / 5
- **Flyweight Cell Rendering Pattern:**
  - Swing uses a **stamp / flyweight rendering mechanism**: a single component instance (e.g. `JLabel`) is repeatedly stamped onto each row or cell during painting. This eliminates the memory cost of creating thousands of widget instances.
  - **`ListCellRenderer`**: Overrides `getListCellRendererComponent()`. Configured custom `ColorDot` icons to signify online status and applied `EmptyBorder` padding for clean readability.
  - **`TableCellRenderer`**: Overrides `getTableCellRendererComponent()`. Inspects cell value type and numerical thresholds (`gpa < 3.0`) to dynamically set foreground colors without altering underlying table data.
  - **`TreeCellRenderer`**: Customizes node label formatting, enabling clean file and folder names rather than raw absolute path strings.

---

### Learning Outcome 3: Menus, Toolbars, and Standard Dialogs
- **Self-Rating:** 5 / 5
- **Dialog & Navigation Hierarchy:**
  - **Menu Architecture**: `JMenuBar` added to top level `JFrame` via `setJMenuBar()`, holding nested `JMenu` dropdowns populated with `JMenuItem` entries and separators.
  - **`JToolBar`**: Configured with `setFloatable(false)` to anchor below the menu bar, providing quick button access and live status indicators.
  - **`JOptionPane`**: Utilized for confirmation prompts (`showConfirmDialog`), error/warning dialogs (`showMessageDialog`), and user input prompts (`showInputDialog`).
  - **`JFileChooser`**: Leveraged OS-native file dialogs configured with `FileNameExtensionFilter` for `.txt` extensions, properly handling approval status (`APPROVE_OPTION`) and IO exceptions.

---

### Learning Outcome 4: Multi-Pane Architecture & Event Coordination
- **Self-Rating:** 5 / 5
- **Event Coordination & Synchronization:**
  - Integrated `TreeSelectionListener` on `JTree` to trigger dynamic data reloading in `JTable` using `tableModel.setRowCount(0)` and `tableModel.addRow()`.
  - Added `MouseListener` adapter on `JTable` detecting double-clicks (`e.getClickCount() == 2`) to enable intuitive folder drilling.
  - Divided viewport dynamically with `JSplitPane.HORIZONTAL_SPLIT`, maintaining adjustable split bounds between navigation and detail views.

---

## Verification & Compilation Commands

All source files compile cleanly with zero errors:

```powershell
# Compile all Week 5 activities
javac (Get-ChildItem -Path week05-advanced-swing -Filter *.java -Recurse | Select-Object -ExpandProperty FullName)

# Run Activity 1: JList Custom Renderer
java -cp week05-advanced-swing/activity1-jlist-renderer ContactListDemo

# Run Activity 2: JTable Student Database
java -cp week05-advanced-swing/activity2-jtable StudentTableDemo

# Run Activity 3: JTree & JTabbedPane Explorer Demo
java -cp week05-advanced-swing/activity3-jtree-tabs FileExplorerDemo

# Run Activity 4: Menus & Dialogs Mini Editor
java -cp week05-advanced-swing/activity4-menus-dialogs MiniEditor

# Run Activity 5: Interactive File Manager Capstone
java -cp week05-advanced-swing/activity5-capstone-file-manager FileManager
```

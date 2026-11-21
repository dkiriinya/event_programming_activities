# Week 3: Building Swing GUIs & Layouts

**Folder:** [`week03-swing-gui/`](../week03-swing-gui/)  
**Run GUI Launcher:** `java -cp week03-swing-gui Week3Launcher`  
**Scripts:** `./scripts/run-week3.sh` (Git Bash/Linux/macOS) or `.\scripts\run-week3.ps1` (PowerShell)

---

## Goals

By the end of Week 3, you should be able to:
- [ ] Create a working Swing window using `JFrame`.
- [ ] Add common Swing components (`JLabel`, `JButton`, `JTextField`, `JCheckBox`, `JComboBox`).
- [ ] Arrange components with **Layout Managers** (`FlowLayout`, `BorderLayout`, `GridLayout`).
- [ ] Run all Swing UI code safely on the **Event Dispatch Thread (EDT)**.

---

## Core Concepts (In Plain English)

- **Swing Hierarchy:** A `JFrame` is the top-level window. You place panels (`JPanel`) inside it, and put buttons, labels, and inputs inside those panels.
- **Layout Managers:** Instead of hardcoding pixel coordinates (which break on different screen sizes), Swing uses layout managers:
  - `FlowLayout`: Places components left-to-right, wrapping to the next line.
  - `BorderLayout`: Splits space into 5 areas: `NORTH`, `SOUTH`, `EAST`, `WEST`, and `CENTER`.
  - `GridLayout`: Arranges components in an equal-sized grid of rows and columns.
- **The EDT Rule:** Always start Swing GUIs inside `SwingUtilities.invokeLater(() -> { ... })` so UI updates happen on Swing's dedicated thread (`AWT-EventQueue-0`).

---

## Activities

### Activity 1: First Swing Window (`activity1-first-window/`)
Creates a basic `JFrame` with a title, size, close operation (`EXIT_ON_CLOSE`), and centered on screen (`setLocationRelativeTo(null)`).
```bash
javac week03-swing-gui/activity1-first-window/FirstWindow.java
java -cp week03-swing-gui/activity1-first-window FirstWindow
```

### Activity 2: Basic Components Tour (`activity2-basic-components/`)
Displays labels, text fields, password fields, checkboxes, radio buttons (grouped with `ButtonGroup`), combo boxes, and scroll panes in one window.
```bash
javac week03-swing-gui/activity2-basic-components/ComponentShowcase.java
java -cp week03-swing-gui/activity2-basic-components ComponentShowcase
```

### Activity 3: Layout Managers (`activity3-layout-managers/`)
Four demos showing how each layout behaves when you resize the window:
- `FlowLayoutDemo.java`
- `BorderLayoutDemo.java`
- `GridLayoutDemo.java`
- `CombinedLayoutDemo.java` (nesting `JPanel`s with different layouts)

```bash
javac week03-swing-gui/activity3-layout-managers/*.java
java -cp week03-swing-gui/activity3-layout-managers CombinedLayoutDemo
```

### Activity 4: The EDT Rule (`activity4-edt-rule/`)
Compares what happens when you block the EDT vs. using a background thread:
- `FreezeDemo.java` — Calls `Thread.sleep(...)` on the EDT, freezing the window.
- `FixedDemo.java` — Moves the slow work to a background `Thread` and updates the UI with `SwingUtilities.invokeLater(...)`.

### Activity 5: Student Portal Mockup (`activity5-complete-mockup/`)
Combines nested panels and layout managers to build a complete static Student Portal UI (`StudentPortal.java`).

```bash
javac week03-swing-gui/activity5-complete-mockup/StudentPortal.java
java -cp week03-swing-gui/activity5-complete-mockup StudentPortal
```

---

## Self-Check

1. **Why use `setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)`?**
   Without it, closing the window hides it but leaves the Java process running in the background.
2. **Which layout manager is best for a calculator keypad?**
   `GridLayout` — every button gets the exact same size in rows and columns.
3. **Why shouldn't you use `setBounds(x, y, w, h)` everywhere?**
   Fixed pixel positions break when the window is resized or opened on different screen resolutions.

See [`week03-swing-gui/SelfAssessment.md`](../week03-swing-gui/SelfAssessment.md) for the full self-assessment.

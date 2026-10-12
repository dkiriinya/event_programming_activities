# Week 3 — Building GUI Applications with Swing: Self-Assessment & Activity Report

---

## Executive Summary & Deliverables Checklist

- [x] **Activity 1: First Swing Window** — `activity1-first-window/FirstWindow.java`
- [x] **Activity 2: Component Showcase** — `activity2-basic-components/ComponentShowcase.java`
- [x] **Activity 3: Layout Managers Deep Dive** — `activity3-layout-managers/` (`FlowLayoutDemo.java`, `BorderLayoutDemo.java`, `GridLayoutDemo.java`, `CombinedLayoutDemo.java`)
- [x] **Activity 4: EDT Rule Verification** — `activity4-edt-rule/` (`FreezeDemo.java`, `FixedDemo.java`)
- [x] **Activity 5: Complete Static GUI Mockup** — `activity5-complete-mockup/StudentPortal.java`
- [x] **Compilation and Verification** — All files compile cleanly with Java 27 without warnings or errors.

---

## Learning Outcomes Self-Assessment

### Learning Outcome 1: Create a functional Swing application with a main window
- **Self-Rating:** 5 / 5
- **Minimal code to create a JFrame (from memory):**

```java
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MinimalWindow {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Minimal Window");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
```

---

### Learning Outcome 2: Add, customize, and arrange components using layouts
- **Self-Rating:** 5 / 5
- **Four Layout Managers & Primary Use Cases:**

| Layout Manager | Characteristics & Behavior | Primary Real-World Use Case |
| :--- | :--- | :--- |
| **`FlowLayout`** | Arranges components left-to-right in natural order; wraps to next line when width is exceeded; preserves component preferred sizes. | Top toolbars, button rows (e.g. Save / Cancel / Close), Google search input rows. |
| **`BorderLayout`** | Divides container into 5 zones (`NORTH`, `SOUTH`, `EAST`, `WEST`, `CENTER`). `NORTH`/`SOUTH` stretch horizontally; `EAST`/`WEST` stretch vertically; `CENTER` expands to fill all leftover space. | Application master window shell (header in `NORTH`, sidebar in `WEST`, workspace in `CENTER`, status bar in `SOUTH`). |
| **`GridLayout`** | Partitions space into a uniform grid of identical-sized rectangular cells across specified rows and columns. | Numeric keypads, calculator button grids, image/photo galleries, dashboard stat tiles. |
| **`BoxLayout`** | Stacks components along a single axis (either vertical `Y_AXIS` or horizontal `X_AXIS`) respecting alignment and rigid spacers/struts. | Vertical control forms, side navigation menus, card stacks, notification feeds. |

---

### Learning Outcome 3: Ensure GUI code runs on the EDT
- **Self-Rating:** 5 / 5
- **The Two Golden Rules of Swing Threading:**
  1. **Rule 1:** All Swing GUI component creation, modification, and event handling must execute strictly on the **Event Dispatch Thread (EDT)**.
  2. **Rule 2:** Never execute long-running, blocking, or computationally intensive operations (e.g. `Thread.sleep()`, heavy database queries, network I/O) on the EDT; offload them to background worker threads and dispatch UI updates back onto the EDT using `SwingUtilities.invokeLater()`.

---

### Learning Outcome 4: Build a static, non-interactive UI layout
- **Self-Rating:** 5 / 5
- **Did `StudentPortal.java` run successfully?**
  - [x] Yes
  - [ ] No
- **Architecture Highlights:**
  - Implements multi-tier nested layouts: `BorderLayout` root frame, `BorderLayout` header with styled branding, `GridLayout` left sidebar navigation, `BoxLayout(Y_AXIS)` scrollable content canvas, and a multi-card `GridLayout` dashboard metric summary.

---

## Detailed Self-Check Questions and Answers

### Activity 1: First Swing Window
- **Q1: What is the difference between `JFrame` and the content pane?**
  - **Answer:** `JFrame` represents the OS top-level window frame containing the border, title bar, icon, and window controls (minimize, maximize, close). The content pane is the internal container surface inside the frame where user interface components (panels, buttons, labels) reside. In modern Java Swing, calling `frame.add(comp)` automatically delegates to `frame.getContentPane().add(comp)`.
- **Q2: What does `EXIT_ON_CLOSE` do?**
  - **Answer:** `JFrame.EXIT_ON_CLOSE` configures the window so that clicking the close button (`X`) invokes `System.exit(0)`, terminating the Java Virtual Machine. This prevents orphan background GUI threads from lingering in memory after the user closes the application window.
- **Q3: Why did we wrap the code in `SwingUtilities.invokeLater()`?**
  - **Answer:** Swing is not thread-safe. Wrapping the creation code in `SwingUtilities.invokeLater()` posts an asynchronous `Runnable` task onto the AWT system event queue (`AWT-EventQueue-0`), ensuring that component initialization and rendering occur strictly on the designated Event Dispatch Thread.

---

### Activity 2: Basic Swing Components Tour
- **Q1: What is the difference between `JTextField` and `JTextArea`?**
  - **Answer:** `JTextField` is designed for single-line plain text inputs (e.g. username, search queries, email addresses) and fires action events on Enter keypresses. `JTextArea` supports multi-line text input (e.g. descriptions, addresses, notes) and supports line-wrapping and paragraph editing.
- **Q2: Why do we put `JRadioButton`s inside a `ButtonGroup`?**
  - **Answer:** `ButtonGroup` creates a logical mutually exclusive group. When radio buttons belong to the same `ButtonGroup`, selecting one button automatically deselects all other radio buttons in that group. Without `ButtonGroup`, radio buttons behave like independent checkboxes.
- **Q3: Why does `JTextArea` need a `JScrollPane`?**
  - **Answer:** `JTextArea` does not implement scrolling mechanisms internally. When wrapped inside a `JScrollPane`, horizontal and vertical scrollbars are dynamically provided whenever text expands past the component's visible bounding dimensions.

---

### Activity 3: Layout Managers Deep Dive
- **Q1: When would you choose `GridLayout` over `FlowLayout`?**
  - **Answer:** Choose `GridLayout` when elements need to have identical size and be arranged into rigid, structured row-and-column cells regardless of the individual text size (e.g. calculator keys, calendar date matrices). Choose `FlowLayout` when components should maintain their preferred sizes and naturally wrap to subsequent rows as the parent container resizes.
- **Q2: Why do we often nest layout managers?**
  - **Answer:** No single layout manager offers complete flexibility for complex user interfaces. Real-world applications decompose the user interface into specialized sub-panels (e.g. toolbars using `FlowLayout`, sidebars using `GridLayout`, body layouts using `BoxLayout`), assembling them together within a master `BorderLayout`.
- **Q3: What does `BorderLayout.CENTER` do differently from the other regions?**
  - **Answer:** While `NORTH`, `SOUTH`, `EAST`, and `WEST` preserve either their preferred height or preferred width, `CENTER` claims all remaining space in both horizontal and vertical dimensions, resizing automatically with the window.

---

### Activity 4: EDT Rule — Why and How
- **Q1: Why does `Thread.sleep()` on the EDT freeze the UI?**
  - **Answer:** The EDT is driven by a single-threaded event dispatch loop processing repaints, mouse clicks, key events, and listener callbacks. Executing `Thread.sleep()` on this thread halts the entire event dispatch loop, rendering the window completely unresponsive to OS paint messages, mouse drag/resize requests, or user inputs.
- **Q2: What is the safe way to update a label from a background thread?**
  - **Answer:** Pass a lambda or `Runnable` to `SwingUtilities.invokeLater(() -> label.setText("Updated!"))`. This safely dispatches the GUI mutation onto the EDT queue without violating thread containment rules.
- **Q3: What happens if you violate the EDT rule?**
  - **Answer:** Violating the EDT rule leads to race conditions, visual artifacts/corruption, stale renders, deadlocks, or intermittent `NullPointerException`s/`IllegalStateException`s. Because Swing components are not synchronized for multi-threaded access, thread safety depends entirely on EDT confinement.

#### Comparative Observation Log: FreezeDemo vs. FixedDemo

| Metric | `FreezeDemo.java` (Antipattern) | `FixedDemo.java` (Best Practice) |
| :--- | :--- | :--- |
| **Execution Thread** | `Thread.sleep(5000)` on `AWT-EventQueue-0` | `Thread.sleep(5000)` on worker `Thread-0` |
| **Window Movement** | Window cannot be dragged or repositioned during task | Window moves smoothly across desktop |
| **Window Resizing** | OS shows "Not Responding"; window fails to repaint | Window redraws and resizes instantaneously |
| **Button State** | Button does not animate or disable | Button disables cleanly ("Working...") and re-enables ("Done!") |
| **Thread Safety** | Direct EDT lockup | Safe EDT handover via `SwingUtilities.invokeLater()` |

---

### Activity 5: Build a Complete Static GUI Mockup
- **Q1: How many different layout managers did you use in this mockup?**
  - **Answer:** At least four layout managers: `BorderLayout` (outer frame, header, stat cards, status bar), `GridLayout` (sidebar button list, stat cards layout), `BoxLayout` (vertical stacking of content cards and notification list), and `FlowLayout` (nested button rows).
- **Q2: Why do we use `Box.createVerticalStrut(20)` between components?**
  - **Answer:** `Box.createVerticalStrut(n)` produces a rigid invisible component with a fixed height of `n` pixels and zero width, providing clean vertical spacing between adjacent controls in a `BoxLayout`.
- **Q3: What is the purpose of `setPreferredSize()` on the sidebar?**
  - **Answer:** `BorderLayout.WEST` respects the preferred width of components while stretching them vertically. Calling `setPreferredSize(new Dimension(180, 0))` guarantees the sidebar keeps an exact width of 180 pixels without collapsing or expanding unnecessarily.

# Week 4 — Interactive Swing Applications: Self-Assessment & Activity Report

---

## Executive Summary & Deliverables Checklist

- [x] **Activity 1: ActionListener Basics** — `activity1-actionlistener-basics/FirstButton.java`
- [x] **Activity 2: Listener Implementation Styles & Event Sources** — `activity2-listener-techniques/ListenerDemo.java` and `EventSourceDemo.java`
- [x] **Activity 3: State & Keystroke Listeners** — `activity3-item-and-document-listeners/ItemListenerDemo.java`, `DocumentListenerDemo.java`, and `LiveSearchDemo.java`
- [x] **Activity 4: Counter App with Keyboard Shortcuts** — `activity4-counter-app/CounterApp.java`
- [x] **Activity 5: Interactive Registration Form** — `activity5-interactive-form/RegistrationForm.java`
- [x] **Activity 6: Mouse & Key Event Handling** — `activity6-mouse-and-key/DrawingApp.java` and `KeyboardMover.java`
- [x] **Clean Compilation & Thread Safety** — All Java sources run strictly on the Event Dispatch Thread (EDT) via `SwingUtilities.invokeLater()` and compile cleanly.

---

## Learning Outcomes Self-Assessment

### Learning Outcome 1: Implement `ActionListener` Using Multiple Techniques
- **Self-Rating:** 5 / 5
- **Listener Implementation Techniques:**

| Technique | Implementation Syntax | Best Use Case |
| :--- | :--- | :--- |
| **Named Class** | `class MyListener implements ActionListener { ... }`<br>`btn.addActionListener(new MyListener());` | Reusable across multiple forms or when complex listener state must be encapsulated. |
| **Anonymous Inner Class** | `btn.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { ... } });` | Legacy codebases (pre-Java 8) or when listener requires multiple overridden methods. |
| **Lambda Expression** | `btn.addActionListener(e -> { ... });` | Modern standard for concise inline event logic. |
| **Method Reference** | `btn.addActionListener(this::handleButtonClick);` | Clean delegation when a descriptive handler method already exists. |

- **Event Source Detection:**
  - `e.getSource()` returns the reference to the `Object` that originated the event (e.g. comparing `source == addButton`).
  - `e.getActionCommand()` returns the string command associated with the component (defaults to the button label if not explicitly overridden).

---

### Learning Outcome 2: Handle State Changes in Checkboxes, Radio Buttons, and Text Fields
- **Self-Rating:** 5 / 5
- **Listener Comparison:**

| Listener Interface | Target Components | Methods | Real-World Use Case |
| :--- | :--- | :--- | :--- |
| **`ItemListener`** | `JCheckBox`, `JRadioButton`, `JComboBox` | `itemStateChanged(ItemEvent e)` | Real-time calculation in order forms (e.g. pizza size & toppings selection). |
| **`DocumentListener`** | `JTextField`, `JTextArea`, `JPasswordField` | `insertUpdate()`, `removeUpdate()`, `changedUpdate()` | Live character counting (e.g. bio limits) and instant autocomplete/filter queries. |

- **DocumentListener Rule:** Never call text-mutating methods (like `setText()`) directly inside `DocumentListener` callbacks on the same document without invoking later or disabling triggers to avoid `IllegalStateException`.

---

### Learning Outcome 3: Differentiate and Map Swing Components to Appropriate Listeners
- **Self-Rating:** 5 / 5
- **Component-to-Listener Matrix:**

| Swing Component | Primary Listener | Secondary / Specialized Listener |
| :--- | :--- | :--- |
| `JButton` | `ActionListener` | `MouseListener` (hover effects / pressed states) |
| `JCheckBox` | `ItemListener` | `ActionListener` |
| `JRadioButton` | `ItemListener` (with `ButtonGroup`) | `ActionListener` |
| `JComboBox` | `ActionListener` / `ItemListener` | `PopupMenuListener` |
| `JTextField` | `DocumentListener` (keystrokes) | `ActionListener` (fires on Enter key) |
| `JTextArea` | `DocumentListener` | `CaretListener` |
| `JSlider` | `ChangeListener` | — |
| `JList` / `JTable` | `ListSelectionListener` | `MouseListener` (double-click row) |
| `JPanel` / Canvas | `MouseListener`, `MouseMotionListener` | `KeyListener` (requires `.setFocusable(true)`) |

---

### Learning Outcome 4: Build Interactive Applications with Validation & Keyboard Navigation
- **Self-Rating:** 5 / 5
- **Key Techniques Applied:**
  1. **Keyboard Hotkeys:** Configured using `rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)` and `rootPane.getActionMap()` rather than fragile `KeyListener`s on individual components, enabling global shortcuts (UP, DOWN, R).
  2. **Comprehensive Form Validation:**
     - Non-empty field checking with trim (`name.isEmpty()`).
     - Email formatting verification (`email.contains("@")`).
     - Minimum length validation (`password.length() >= 6`).
     - Confirmation dialogs for destructive actions (`JOptionPane.showConfirmDialog`).
  3. **Low-Level Event Adapters:**
     - `MouseAdapter` allows overriding only `mousePressed` and `mouseDragged` without empty stubs for `mouseReleased`, `mouseEntered`, `mouseExited`.
     - `KeyAdapter` provides simple key interception (`keyPressed`) with coordinate boundary bounding (`Math.max`, `Math.min`).

---

## Verification & Execution Guide

All applications can be compiled and executed directly from the project root:

```powershell
# Activity 1: First Button
javac week04-event-handling/activity1-actionlistener-basics/FirstButton.java
java -cp week04-event-handling/activity1-actionlistener-basics FirstButton

# Activity 2: Listener Techniques & Event Sources
javac week04-event-handling/activity2-listener-techniques/*.java
java -cp week04-event-handling/activity2-listener-techniques ListenerDemo
java -cp week04-event-handling/activity2-listener-techniques EventSourceDemo

# Activity 3: Item and Document Listeners
javac week04-event-handling/activity3-item-and-document-listeners/*.java
java -cp week04-event-handling/activity3-item-and-document-listeners ItemListenerDemo
java -cp week04-event-handling/activity3-item-and-document-listeners DocumentListenerDemo
java -cp week04-event-handling/activity3-item-and-document-listeners LiveSearchDemo

# Activity 4: Counter App
javac week04-event-handling/activity4-counter-app/CounterApp.java
java -cp week04-event-handling/activity4-counter-app CounterApp

# Activity 5: Registration Form
javac week04-event-handling/activity5-interactive-form/RegistrationForm.java
java -cp week04-event-handling/activity5-interactive-form RegistrationForm

# Activity 6: Mouse and Key Event Handling
javac week04-event-handling/activity6-mouse-and-key/*.java
java -cp week04-event-handling/activity6-mouse-and-key DrawingApp
java -cp week04-event-handling/activity6-mouse-and-key KeyboardMover
```

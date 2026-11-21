# Week 4: Event Handling in Swing

**Folder:** [`week04-event-handling/`](../week04-event-handling/)  
**Run GUI Launcher:** `java -cp week04-event-handling Week4Launcher`  
**Scripts:** `./scripts/run-week4.sh` (Git Bash/Linux/macOS) or `.\scripts\run-week4.ps1` (PowerShell)

---

## Goals

By the end of Week 4, you should be able to:
- [ ] Handle button clicks with `ActionListener` (using anonymous classes, lambdas, and separate classes).
- [ ] Track checkbox/radio changes with `ItemListener` and live typing with `DocumentListener`.
- [ ] Handle mouse and keyboard events (`MouseListener`, `KeyListener`).
- [ ] Build interactive Swing apps with keyboard shortcuts and form validation.

---

## Core Concepts (In Plain English)

| Listener | Fires When... | Common Use |
| :--- | :--- | :--- |
| `ActionListener` | A button is clicked or Enter is pressed | Buttons, menu items, submit actions |
| `ItemListener` | An item is selected or deselected | Checkboxes, radio buttons, dropdowns |
| `DocumentListener` | Text changes (`insert`, `remove`, `change`) | Live search bars, character counters |
| `MouseListener` / `MouseMotionListener` | Mouse clicks, enters, exits, or drags | Drawing canvases, hover effects |
| `KeyListener` | Keys are pressed or released | Shortcuts, moving objects on screen |

**Using Lambdas:** Since `ActionListener` only has one method (`actionPerformed`), you can write:
```java
button.addActionListener(e -> label.setText("Clicked!"));
```

---

## Activities

### Activity 1: First Interactive Button (`activity1-actionlistener-basics/`)
A simple window where clicking a button updates a label (`FirstButton.java`).

### Activity 2: Listener Styles & Event Sources (`activity2-listener-techniques/`)
- `ListenerDemo.java` — Shows 3 ways to write a listener: inner class, anonymous class, and lambda.
- `EventSourceDemo.java` — Shares one listener across multiple buttons and uses `e.getSource()` / `e.getActionCommand()` to tell which button was clicked.

### Activity 3: `ItemListener` and `DocumentListener` (`activity3-item-and-document-listeners/`)
- `ItemListenerDemo.java` — Reacts to checkboxes and radio buttons as soon as they change.
- `DocumentListenerDemo.java` — Counts characters in real time as you type.
- `LiveSearchDemo.java` — Filters a list of items instantly while typing.

### Activity 4: Counter App with Shortcuts (`activity4-counter-app/`)
`CounterApp.java` — Increment, decrement, and reset a counter with buttons and keyboard shortcuts.

### Activity 5: Interactive Registration Form (`activity5-interactive-form/`)
`RegistrationForm.java` — Validates name, email, and password fields and shows feedback when submitted.

### Activity 6: Mouse & Keyboard Events (`activity6-mouse-and-key/`)
- `DrawingApp.java` — Click and drag to draw on a panel using mouse events.
- `KeyboardMover.java` — Move a shape around the screen using arrow keys or WASD.

---

## Self-Check

1. **When should you use `DocumentListener` instead of `ActionListener` on a `JTextField`?**
   Use `DocumentListener` when you want to react on *every keystroke* (like live search). `ActionListener` only fires when the user presses Enter.
2. **How can one `ActionListener` handle 4 different buttons?**
   Check `e.getSource() == myButton` or `e.getActionCommand().equals("Save")` inside the listener.
3. **Why do Mouse/Key adapters (`MouseAdapter`) exist?**
   Interfaces like `MouseListener` have 5 methods. Extending `MouseAdapter` lets you override only the 1 or 2 methods you actually need.

See [`week04-event-handling/SelfAssessment.md`](../week04-event-handling/SelfAssessment.md) for the full self-assessment.

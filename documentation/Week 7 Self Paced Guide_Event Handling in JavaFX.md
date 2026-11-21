# Week 7: Event Handling in JavaFX

**Folder:** [`week07-javafx-events/`](../week07-javafx-events/)  
**Run GUI Launcher:** `mvn -f week07-javafx-events/pom.xml javafx:run`  
**Scripts:** `./scripts/run-week7.sh` (Git Bash/Linux/macOS) or `.\scripts\run-week7.ps1` (PowerShell)

---

## Goals

By the end of Week 7, you should be able to:
- [ ] Explain the two phases of JavaFX event delivery: **Capturing** (top-down) and **Bubbling** (bottom-up).
- [ ] Use **Event Filters** (`addEventFilter`) vs. **Event Handlers** (`addEventHandler`).
- [ ] Handle mouse and keyboard events in JavaFX.
- [ ] Implement drag-and-drop between UI controls.
- [ ] Create and fire your own custom `Event` class.

---

## Core Concepts (In Plain English)

When you click a button inside a panel inside a window, JavaFX delivers the event in two passes:
1. **Capturing Phase (Top → Down):** Starts at the `Stage` → `Scene` → `VBox` → `Button`. **Event Filters** run here. Calling `event.consume()` stops the event before it even reaches the button.
2. **Bubbling Phase (Bottom → Up):** Goes back up from `Button` → `VBox` → `Scene` → `Stage`. **Event Handlers** (like `setOnAction`) run here.

---

## Activities

### Activity 1: Event Propagation (`EventPropagationDemo.java`)
Logs the exact order that Filters (capturing) and Handlers (bubbling) fire when you click a nested button, plus a toggle to test `event.consume()`.
```bash
mvn -f week07-javafx-events/pom.xml javafx:run -Djavafx.mainClass=com.example.EventPropagationDemo
```

### Activity 2: Mouse & Keyboard Events (`MouseKeyDemo.java`)
Tracks mouse clicks, coordinates, and key presses (`KeyCode`), including `Ctrl` / `Shift` modifier checks.
```bash
mvn -f week07-javafx-events/pom.xml javafx:run -Djavafx.mainClass=com.example.MouseKeyDemo
```

### Activity 3: Drag and Drop (`DragDropDemo.java`)
Drag text or items from a source label onto a target box using `setOnDragDetected`, `setOnDragOver`, and `setOnDragDropped`.
```bash
mvn -f week07-javafx-events/pom.xml javafx:run -Djavafx.mainClass=com.example.DragDropDemo
```

### Activity 4: Custom Events (`LoginSuccessEvent.java`, `CustomEventDemo.java`)
Defines a custom `LoginSuccessEvent` extending `javafx.event.Event` and fires it with `fireEvent(...)` so other components can listen for it cleanly.
```bash
mvn -f week07-javafx-events/pom.xml javafx:run -Djavafx.mainClass=com.example.CustomEventDemo
```

---

## Self-Check

1. **Which runs first: an Event Filter or an Event Handler?**
   The Event Filter (during the top-down capturing phase).
2. **What does `event.consume()` do?**
   It marks the event as handled and stops it from travelling any further up or down the node tree.
3. **In Drag-and-Drop, why must you call `event.acceptTransferModes(...)` inside `setOnDragOver`?**
   Without it, JavaFX assumes the target doesn't accept drops, and `setOnDragDropped` will never fire.

See [`week07-javafx-events/SelfAssessment.md`](../week07-javafx-events/SelfAssessment.md) for the full self-assessment.

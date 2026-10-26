# Week 7 — Event Handling in JavaFX: Self-Assessment & Activity Report

---

## Executive Summary & Deliverables Checklist

- [x] **Activity 1: Event Filters vs. Event Handlers** — [`src/main/java/com/example/EventPropagationDemo.java`](src/main/java/com/example/EventPropagationDemo.java)
  - Demonstrated the two phases of JavaFX event propagation: **Capturing (Filters)** and **Bubbling (Handlers)**.
  - Intercepted click events at the root node using `addEventFilter(MouseEvent.MOUSE_CLICKED, ...)` before reaching the button.
  - Showcased event consumption with `e.consume()` to halt propagation.
  - Handled the bubbling phase using `addEventHandler(MouseEvent.MOUSE_CLICKED, ...)` and convenience method `btn.setOnMouseClicked(...)`.
- [x] **Activity 2: Mouse and Key Events** — [`src/main/java/com/example/MouseKeyDemo.java`](src/main/java/com/example/MouseKeyDemo.java)
  - Tracked continuous mouse cursor coordinates in real time using `root.setOnMouseMoved()`.
  - Handled keyboard arrow inputs (`UP`, `DOWN`, `LEFT`, `RIGHT`) via `root.setOnKeyPressed()` and `e.getCode()`.
  - Controlled focus management using `root.setFocusTraversable(true)` and `primaryStage.setOnShown(e -> root.requestFocus())`.
  - Implemented boundary collision clamping (`Math.max` / `Math.min`) keeping the player circle within window limits.
- [x] **Activity 3: Drag and Drop Interaction** — [`src/main/java/com/example/DragDropDemo.java`](src/main/java/com/example/DragDropDemo.java)
  - Initialized drag gesture from drag source label using `itemToDrag.setOnDragDetected()`, `Dragboard`, `ClipboardContent`, and `TransferMode.MOVE`.
  - Configured drop target rectangle with `dropZone.setOnDragOver()` and `event.acceptTransferModes()`.
  - Provided interactive visual feedback on hover using `setOnDragEntered()` (`LIGHTGREEN`) and `setOnDragExited()` (`LIGHTGRAY`).
  - Implemented final drop resolution via `setOnDragDropped()`, verifying `db.hasString()`, disabling source, and calling `event.setDropCompleted(true)`.
- [x] **Activity 4: Custom Events & Loose Coupling** — [`src/main/java/com/example/LoginSuccessEvent.java`](src/main/java/com/example/LoginSuccessEvent.java) & [`src/main/java/com/example/CustomEventDemo.java`](src/main/java/com/example/CustomEventDemo.java)
  - Defined custom event class `LoginSuccessEvent` extending `javafx.event.Event` with static `EventType<LoginSuccessEvent> LOGIN_SUCCESS`.
  - Dispatched custom event across node hierarchy using `root.fireEvent(new LoginSuccessEvent(username))`.
  - Separated UI submission trigger from event-handling consumer using `root.addEventHandler(LoginSuccessEvent.LOGIN_SUCCESS, ...)`.
- [x] **Master Activity Launcher** — [`src/main/java/com/example/Week7Launcher.java`](src/main/java/com/example/Week7Launcher.java)
  - JavaFX graphical dashboard for one-click launching of any Week 7 activity or opening all 4 concurrently.

---

## Learning Outcomes Self-Assessment

### Learning Outcome 1: Handle Action, Key, and Mouse Events
- **Self-Rating:** 5 / 5
- **Implementation Highlights:**
  - Mouse motion is tracked without clicking via `setOnMouseMoved()`, reading `e.getX()` and `e.getY()` to update real-time coordinates.
  - Keyboard interactions require proper focus targeting; calling `root.setFocusTraversable(true)` and `primaryStage.setOnShown(e -> root.requestFocus())` guarantees arrow keys are received.
  - Discrete keys are identified with `e.getCode() == KeyCode.UP`, allowing bounded character motion.

### Learning Outcome 2: Differentiate Between Event Filters and Event Handlers
- **Self-Rating:** 5 / 5
- **Event Propagation Lifecycle:**
  1. **Capturing Phase (Top-Down):** The event travels from the `Stage` → `Scene` → `Root Node` → child containers down to the target node. Registered **Event Filters** (`addEventFilter`) run during this phase. Calling `e.consume()` here stops the event from ever reaching the target.
  2. **Target Phase:** The event reaches the target node (e.g. `Button`).
  3. **Bubbling Phase (Bottom-Up):** The event bubbles back up from the target node through its parents to the root. Registered **Event Handlers** (`addEventHandler`, `setOnMouseClicked`) run during this phase.
- **Observed Execution Order in Console:**
  ```text
  1. [FILTER] Root intercepted the click!   (Capturing phase)
  2. [HANDLER] Button was clicked!           (Target phase)
  3. [HANDLER] Root saw the click bubbling up! (Bubbling phase)
  ```

### Learning Outcome 3: Implement Drag-and-Drop Functionality
- **Self-Rating:** 5 / 5
- **Drag-and-Drop Pipeline:**
  - **Source (`setOnDragDetected`):** Invokes `startDragAndDrop(TransferMode.MOVE)`, populates `ClipboardContent`, and supplies data to `Dragboard`.
  - **Target Over (`setOnDragOver`):** Validates transfer mode and content type; calls `acceptTransferModes(TransferMode.MOVE)` to indicate willingness to accept the payload.
  - **Visual Feedback (`setOnDragEntered` / `setOnDragExited`):** Dynamically changes border / fill colors (`LIGHTGREEN` on hover, `LIGHTGRAY` on exit).
  - **Drop (`setOnDragDropped`):** Extracts payload via `Dragboard.getString()`, updates UI state, disables the source item, and marks `event.setDropCompleted(true)`.

### Learning Outcome 4: Create and Fire Custom Events
- **Self-Rating:** 5 / 5
- **Decoupled Architecture:**
  - Instead of having a login button directly manipulate every UI label or downstream service, it fires a typed `LoginSuccessEvent`.
  - Any component anywhere in the scene hierarchy can listen for `LoginSuccessEvent.LOGIN_SUCCESS` without the button having any reference to it.
  - This promotes the Single Responsibility Principle and loose coupling in enterprise applications.

---

## Handbook Self-Check Questions & Answers

### Activity 1: Event Filters vs. Handlers
1. **Q: Which phase of event propagation does an Event Filter belong to?**
   - **A:** The **Capturing Phase**. It travels top-down from the root container towards the target node.
2. **Q: What does the `e.consume()` method do?**
   - **A:** It halts further event propagation immediately. If called inside an event filter, the event will never reach the target node or any bubbling event handlers.
3. **Q: How do you register an Event Handler (Bubbling phase) in code?**
   - **A:** Using `node.addEventHandler(eventType, handler)` or convenience setter methods like `node.setOnMouseClicked(...)` and `node.setOnAction(...)`.

### Activity 2: Mouse and Key Events
1. **Q: Which method handles tracking the mouse's position as it moves without clicking?**
   - **A:** `setOnMouseMoved(EventHandler<MouseEvent>)`.
2. **Q: Why might a `KeyEvent` not fire when you press a key?**
   - **A:** Because the target component does not have input focus. The node must be focus-traversable (`setFocusTraversable(true)`) and must request focus (`root.requestFocus()`).
3. **Q: How do you check which physical key was pressed in a `KeyEvent`?**
   - **A:** By calling `e.getCode()`, which returns a `KeyCode` enum constant (e.g. `KeyCode.UP`, `KeyCode.ENTER`).

### Activity 3: Drag and Drop
1. **Q: Which event handler must be called on the source to initiate a drag operation?**
   - **A:** `setOnDragDetected(EventHandler<MouseEvent>)`.
2. **Q: What is the purpose of `event.acceptTransferModes(TransferMode.MOVE)` in the `setOnDragOver` handler?**
   - **A:** It signals to the JavaFX drag-and-drop subsystem that this target accepts the incoming drop. Without calling this method, the drop gesture will be rejected.
3. **Q: How do you extract the data from the dragged item when it is dropped?**
   - **A:** By calling `event.getDragboard()` and reading its contents (e.g. `db.getString()`).

### Activity 4: Custom Events
1. **Q: Why would you use a Custom Event instead of just calling a method directly?**
   - **A:** To achieve **loose coupling**. The emitter of the event does not need to know about or maintain direct references to any listening components, making the codebase modular and easily extensible.
2. **Q: Which class must your custom event extend?**
   - **A:** `javafx.event.Event`.
3. **Q: How do you define the type of your custom event?**
   - **A:** By creating a `public static final EventType<MyEvent>` field (e.g. `new EventType<>(Event.ANY, "LOGIN_SUCCESS")`).

---

## How to Run & Verify

### Option 1: Modern Maven Runner (Recommended)
```bash
cd week07-javafx-events

# Launch Master GUI Dashboard:
mvn javafx:run

# Or run specific activities:
mvn javafx:run -Djavafx.mainClass=com.example.EventPropagationDemo
mvn javafx:run -Djavafx.mainClass=com.example.MouseKeyDemo
mvn javafx:run -Djavafx.mainClass=com.example.DragDropDemo
mvn javafx:run -Djavafx.mainClass=com.example.CustomEventDemo
```

### Option 2: PowerShell Runner Scripts
```powershell
.\scripts\run-week7.ps1             # Interactive menu
.\scripts\run-week7.ps1 -Launcher   # Open JavaFX Master Dashboard
.\scripts\run-week7.ps1 -All        # Launch all 4 activities concurrently
.\scripts\run-week7.ps1 -Activity 1 # Run specific activity (1 to 4)
```

### Option 3: Git Bash / Linux / macOS Runner Scripts
```bash
./scripts/run-week7.sh              # Interactive menu
./scripts/run-week7.sh --launcher   # Open JavaFX Master Dashboard
./scripts/run-week7.sh --all        # Launch all 4 activities concurrently
```

### Option 4: AI Agent CLI (`agent-cli`)
```bash
./agent-cli list --week 7 --json    # View activity metadata schema
./agent-cli verify --week 7         # Verify bytecode compilation & patterns
./agent-cli grade --week 7 --json   # Automated grading rubric
```

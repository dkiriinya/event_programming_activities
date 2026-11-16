# Week 9 — Asynchronous Programming in JavaFX: Self-Assessment & Activity Report

---

## Executive Summary & Deliverables Checklist

- [x] **Activity 1: The Freeze (Blocking Operations)** — [`src/main/java/com/example/FreezeDemo.java`](src/main/java/com/example/FreezeDemo.java)
  - Intentionally demonstrates the anti-pattern of executing `Thread.sleep(5000)` directly inside a JavaFX `setOnAction` handler on the **JavaFX Application Thread**.
  - Illustrates why window dragging, resizing, and UI repaints hang until the blocking work completes.
- [x] **Activity 2: The Fix (`Task` & Safe UI Updates)** — [`src/main/java/com/example/TaskDemo.java`](src/main/java/com/example/TaskDemo.java)
  - Offloads the 5-second blocking simulation into `javafx.concurrent.Task<Void>` running on a background daemon `Thread`.
  - Uses `Platform.runLater(...)` inside `task.setOnSucceeded(...)` to safely marshal UI updates (`statusLabel.setText(...)`, `startButton.setDisable(false)`) back onto the JavaFX Application Thread.
  - *Note on handbook snippet correction:* Replaced Swing-style `startButton.setEnabled(...)` in the guide with JavaFX's `startButton.setDisable(...)` so the project compiles cleanly against JavaFX 21.
- [x] **Activity 3: Live Progress (Real-Time Determinate Feedback)** — [`src/main/java/com/example/ProgressDemo.java`](src/main/java/com/example/ProgressDemo.java)
  - Simulates downloading 100 chunks on a background `Task<Void>` while calling `updateProgress(i, 100)` and `updateMessage("Downloading... " + i + "%")`.
  - Binds `progressBar.progressProperty()` and `statusLabel.textProperty()` directly to the task's observable properties.
  - Calls `statusLabel.textProperty().unbind()` in `setOnSucceeded` before setting `"Download Complete!"` manually.
  - *Note on handbook snippet correction:* Replaced `startButton.setEnabled(...)` with `startButton.setDisable(...)` for JavaFX API compatibility.
- [x] **Activity 4: The `Service` Class (Capstone — Restartable Background Job)** — [`src/main/java/com/example/ServiceDemo.java`](src/main/java/com/example/ServiceDemo.java)
  - Implements `DataSyncService extends Service<String>` whose `createTask()` constructs a cancellable background `Task<String>` checking `isCancelled()`.
  - Supports `service.restart()`, `service.cancel()`, indeterminate `ProgressIndicator`, and `setOnSucceeded` / `setOnFailed` lifecycle callbacks.
- [x] **Master Activity Launcher** — [`src/main/java/com/example/Week9Launcher.java`](src/main/java/com/example/Week9Launcher.java)
  - JavaFX graphical dashboard for launching any Week 9 activity individually or opening all 4 concurrently.

---

## Learning Outcomes Self-Assessment

### Learning Outcome 1: Identify Operations That Freeze the UI
- **Self-Rating:** 5 / 5
- **Implementation Highlights:**
  - Any long-running I/O, network request, database query, or `Thread.sleep()` executed inside an event handler blocks the single JavaFX Application Thread responsible for input dispatch and scene rendering.
  - Demonstrated in `FreezeDemo` where clicking **"Freeze the UI!"** stalls the entire window for 5 seconds.

### Learning Outcome 2: Implement Background Tasks Using `Task` and `Service`
- **Self-Rating:** 5 / 5
- **Implementation Highlights:**
  - `Task<V>` encapsulates a one-shot background computation inside `protected V call() throws Exception`, executed on a worker `Thread`.
  - `Service<V>` wraps a `Task` factory (`createTask()`) to provide reusable, restartable, and cancellable background operations (`restart()`, `cancel()`), ideal for actions like dashboard syncs.

### Learning Outcome 3: Safely Update the UI from Background Threads
- **Self-Rating:** 5 / 5
- **Implementation Highlights:**
  - JavaFX scene graph nodes are not thread-safe and must only be mutated on the JavaFX Application Thread.
  - Demonstrated both `Platform.runLater(() -> ...)` for arbitrary UI updates and JavaFX `Task` lifecycle callbacks (`setOnSucceeded`, `setOnFailed`) which automatically run on the FX Application Thread.

### Learning Outcome 4: Provide Real-Time Progress Feedback to the User
- **Self-Rating:** 5 / 5
- **Implementation Highlights:**
  - **Determinate feedback:** `ProgressBar` bound to `downloadTask.progressProperty()` updated via `updateProgress(workDone, max)`.
  - **Live status text:** `Label` bound to `downloadTask.messageProperty()` updated via `updateMessage(msg)`, then cleanly unbound with `unbind()` upon completion.
  - **Indeterminate feedback:** `ProgressIndicator` spinner toggled during `ServiceDemo` execution.

---

## Handbook Self-Check Questions & Answers

### Activity 1: The Freeze
1. **Q: Why did the window freeze for 5 seconds?**
   - **A:** Because `Thread.sleep(5000)` was executed directly on the JavaFX Application Thread inside the button's event handler, preventing the thread from processing window movement, resizing, input events, or screen repaints for 5 seconds.

### Activity 2: The Fix
1. **Q: Why did we use `Platform.runLater()` inside `setOnSucceeded`?**
   - **A:** To explicitly ensure UI mutations (`statusLabel.setText("Task Complete!")` and re-enabling `startButton`) are scheduled onto the JavaFX Application Thread rather than risking off-thread scene graph access. *(Note: in JavaFX, `Task`'s `setOnSucceeded` callback itself is already invoked on the FX Application Thread, while `Platform.runLater()` is required whenever dispatching UI updates from a raw background thread.)*

### Activity 3: Live Progress
1. **Q: What is the difference between `updateProgress()` and `Platform.runLater()`?**
   - **A:** `updateProgress()` is a built-in `Task` method designed specifically to throttle/coalesce and safely publish numeric progress updates to `progressProperty()` on the FX Application Thread, whereas `Platform.runLater()` queues an arbitrary `Runnable` onto the FX Application Thread.
2. **Q: Why did we call `statusLabel.textProperty().unbind()` at the end?**
   - **A:** Because `statusLabel.textProperty()` was bound to `downloadTask.messageProperty()`. Calling `statusLabel.setText(...)` on a bound property throws a `RuntimeException` (`A bound value cannot be set`), so unbinding first is required before setting the final message manually.

### Activity 4: The `Service` Class
1. **Q: What is the main difference between `Task` and `Service`?**
   - **A:** A `Task` represents a single-use background job that cannot be restarted once executed. A `Service` manages `Task` creation via `createTask()` and can be started, cancelled, and restarted repeatedly (`service.restart()`), making it ideal for repeatable operations like refreshing or syncing data.
2. **Q: How do you check inside the `call()` method if the user clicked "Cancel"?**
   - **A:** By checking `isCancelled()` inside the work loop so the background thread can break out cleanly and abort remaining work immediately.

---

## How to Run & Verify

### Option 1: Maven Runner (Recommended)
```bash
cd week09-async-javafx

# Launch Master GUI Dashboard:
mvn javafx:run

# Or run specific activities:
mvn javafx:run -Djavafx.mainClass=com.example.FreezeDemo
mvn javafx:run -Djavafx.mainClass=com.example.TaskDemo
mvn javafx:run -Djavafx.mainClass=com.example.ProgressDemo
mvn javafx:run -Djavafx.mainClass=com.example.ServiceDemo
```

### Option 2: Convenience Scripts (From Repository Root)
```powershell
.\scripts\run-week9.ps1 -Launcher   # Open Week 9 GUI Dashboard
.\scripts\run-week9.ps1 -All        # Launch all 4 activities concurrently
.\scripts\run-week9.ps1 -Activity 3 # Run ProgressDemo
```

```bash
./scripts/run-week9.sh --launcher   # Open Week 9 GUI Dashboard
./scripts/run-week9.sh --all        # Launch all 4 activities concurrently
./scripts/run-week9.sh 3            # Run ProgressDemo
```

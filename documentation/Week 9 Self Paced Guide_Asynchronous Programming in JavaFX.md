# Week 9: Asynchronous Programming in JavaFX

**Folder:** [`week09-async-javafx/`](../week09-async-javafx/)  
**Run GUI Launcher:** `mvn -f week09-async-javafx/pom.xml javafx:run`  
**Scripts:** `./scripts/run-week9.sh` (Git Bash/Linux/macOS) or `.\scripts\run-week9.ps1` (PowerShell)

---

## Goals

By the end of Week 9, you should be able to:
- [ ] Explain why slow work freezes a JavaFX app.
- [ ] Run background work off the UI thread using `javafx.concurrent.Task`.
- [ ] Report live progress (`updateProgress`, `updateMessage`) safely to a `ProgressBar` and `Label`.
- [ ] Build reusable, restartable background jobs with `javafx.concurrent.Service`.

---

## Core Concepts (In Plain English)

Just like Swing has the EDT, JavaFX has the **JavaFX Application Thread**. It handles all drawing, animations, and button clicks.
- **Rule 1:** Never run slow work (database queries, network calls, `Thread.sleep`) on the JavaFX Application Thread — the app will freeze.
- **Rule 2:** Never update UI controls directly from a background thread — use `Platform.runLater(...)` or `Task` bindings.

**`Task` vs `Service`:**
- **`Task<V>`:** A one-time background job. Override `call()`, use `setOnSucceeded` / `setOnFailed` for results, and run it in a `new Thread(task).start()`.
- **`Service<V>`:** A reusable factory for `Task`s. Call `service.restart()` or `service.cancel()` as many times as you want.

---

## Activities

### Activity 1: The Freeze Problem (`FreezeDemo.java`)
Runs `Thread.sleep(5000)` directly on a button click so you can see the window completely lock up.
```bash
mvn -f week09-async-javafx/pom.xml javafx:run -Djavafx.mainClass=com.example.FreezeDemo
```

### Activity 2: Fixing It with `Task` (`TaskDemo.java`)
Moves the 5-second work into a `Task<Void>` on a background thread and updates the status label safely when it finishes.
```bash
mvn -f week09-async-javafx/pom.xml javafx:run -Djavafx.mainClass=com.example.TaskDemo
```

### Activity 3: Live Progress (`ProgressDemo.java`)
Simulates a file download using `updateProgress(i, 100)` and `updateMessage(...)`, bound directly to a `ProgressBar` and `Label`.
```bash
mvn -f week09-async-javafx/pom.xml javafx:run -Djavafx.mainClass=com.example.ProgressDemo
```

### Activity 4: Reusable `Service` (`ServiceDemo.java`)
Implements a `DataSyncService extends Service<String>` with Start, Cancel (`isCancelled()`), and Restart support.
```bash
mvn -f week09-async-javafx/pom.xml javafx:run -Djavafx.mainClass=com.example.ServiceDemo
```

---

## Self-Check

1. **Can you reuse a `Task` after it finishes?**
   No — a `Task` is single-use. Use a `Service` if you need to run the same job multiple times.
2. **Why is binding `progressBar.progressProperty().bind(task.progressProperty())` safe even though `call()` runs on a background thread?**
   `updateProgress()` and `updateMessage()` automatically marshal updates onto the JavaFX Application Thread for you.
3. **When do you need `Platform.runLater(...)`?**
   Whenever you're on a plain background `Thread` (not using `Task` callbacks) and need to update a JavaFX UI node.

See [`week09-async-javafx/SelfAssessment.md`](../week09-async-javafx/SelfAssessment.md) for the full self-assessment.

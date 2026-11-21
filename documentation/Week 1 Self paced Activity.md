# Week 1: Event-Driven Programming Basics

**Folder:** [`Week1_Activity/`](../Week1_Activity/)  
**Run:** `./scripts/run-week1.sh` (Git Bash/Linux/macOS) or `.\scripts\run-week1.ps1` (PowerShell)

---

## Goals

By the end of Week 1, you should be able to:
- [ ] Identify the 4 core parts of an event-driven system: **Event**, **Source**, **Listener**, and **Handler**.
- [ ] Explain how event-driven programming differs from procedural programming.
- [ ] Describe how the **event loop** and **event queue** work (and why you shouldn't block the UI thread).
- [ ] Spot and fix common Swing EDT bugs.

---

## Core Concepts (In Plain English)

Every event-driven app has four parts:
1. **Event:** Something that happened (a button click, a key press, a timer firing).
2. **Event Source:** The component that created the event (a `JButton`, a sensor, a text box).
3. **Event Listener:** The object waiting for that event to happen (`ActionListener`).
4. **Event Handler:** The code that runs when the event arrives (e.g., updating a label).

**How the Event Loop works:**
Events go into a queue in order. A single background loop (in Swing, this is the **Event Dispatch Thread / EDT**) picks up one event at a time and runs its handler. If a handler takes 5 seconds (like `Thread.sleep(5000)`), the whole UI freezes until it finishes.

---

## Activities

### Activity 1: Spot the Components
Look at everyday systems (an ATM, microwave, traffic light, elevator, or game) and break down their 4 parts:

| System | Event Source | Event | Listener | Handler |
| :--- | :--- | :--- | :--- | :--- |
| ATM | Keypad button | Button pressed | ATM software | Check PIN |
| ATM | Card slot | Card inserted | Card reader | Read card data |
| Microwave | Start button | Button clicked | Controller | Turn on magnetron |

Try adding 3 more systems of your own (e.g. smartphone screen, car dashboard, online store).

---

### Activity 2: Procedural vs. Event-Driven Calculators
Compare how a console program runs top-to-bottom versus how a GUI waits for clicks.

1. **Procedural (`ProceduralCalculator.java`):** Asks for number 1, then number 2, then operator, prints result, and exits.
   ```bash
   javac Week1_Activity/ProceduralCalculator.java
   java -cp Week1_Activity ProceduralCalculator
   ```
2. **Event-Driven (`EventDrivenCalculator.java`):** Opens a Swing window and waits. Nothing happens until you click a button.
   ```bash
   javac Week1_Activity/EventDrivenCalculator.java
   java -cp Week1_Activity EventDrivenCalculator
   ```

**Key takeaway:** Procedural code controls the order of steps. Event-driven code reacts to whatever the user does next.

---

### Activity 3: Debug the Event Handler (`BugFixChallenge/`)
Four small programs in [`Week1_Activity/BugFixChallenge/`](../Week1_Activity/BugFixChallenge/) show common Swing bugs and how to fix them:

1. **Blocking the EDT (`Bug1_BlockingEDT.java`)**
   - **Problem:** Calling `Thread.sleep(5000)` inside a button click freezes the entire window.
   - **Fix:** Run slow work in a background `Thread`, then update the label with `SwingUtilities.invokeLater(...)`.

2. **Missing Listener (`Bug2_MissingListener.java`)**
   - **Problem:** Clicking the button does nothing.
   - **Fix:** Attach a listener with `incrementButton.addActionListener(e -> { ... })`.

3. **Updating UI from the Wrong Thread (`Bug3_WrongThread.java`)**
   - **Problem:** A background thread calls `timeLabel.setText(...)` directly, which can cause UI glitches.
   - **Fix:** Wrap UI updates in `SwingUtilities.invokeLater(() -> timeLabel.setText(...))`.

4. **Memory Leak (`Bug4_MemoryLeak.java`)**
   - **Problem:** Removing buttons from the panel doesn't remove them from the `createdButtons` list, so memory keeps growing.
   - **Fix:** Call `createdButtons.clear()` when removing the buttons.

See [`BugFixes.md`](../Week1_Activity/BugFixChallenge/BugFixes.md) for the full write-up.

---

### Activity 4: Smart Home Prototype (`SmartHomePrototype.java`)
A small Swing app simulating lights, thermostat, and door sensors using events and listeners.

```bash
javac Week1_Activity/SmartHomePrototype.java
java -cp Week1_Activity SmartHomePrototype
```

---

## Self-Check

1. **What's the difference between an event source and an event listener?**
   The source creates the event (e.g. a button). The listener waits for it and runs code when it happens.
2. **Why should you never run long tasks on the EDT?**
   Because the EDT handles all drawing and clicks. Blocking it freezes the entire UI.
3. **How do you safely update a Swing label from a background thread?**
   Use `SwingUtilities.invokeLater(() -> label.setText("..."))`.

More questions and answers are in [`Week1_Activity/SelfAssessment.md`](../Week1_Activity/SelfAssessment.md).

# Week 1 â€” Event-Driven Programming: Self-Assessment & Deliverables

This document contains the completed student deliverables and self-assessments for all activities in **Week 1: Introduction to Event-Driven Programming**.

---

## Activity 1 Deliverable: Real-World EDP Scavenger Hunt

### 8 Systems Component Analysis Table

| # | System | Event Source | Event | Listener | Handler / Action |
| :-: | :--- | :--- | :--- | :--- | :--- |
| **1** | **ATM Machine** | Numeric keypad buttons | Key press (`PIN entered`) | ATM terminal controller | Verifies PIN hash against banking server |
| **2** | **ATM Machine** | Motorized card slot | Smartcard inserted | Chip card reader subsystem | Reads account payload and displays greeting |
| **3** | **Microwave Oven** | Dial / Start button | Timer dial turned & Start pressed | Microwave microcontroller | Energizes magnetron tube and starts countdown |
| **4** | **Traffic Light** | Inductive vehicle loop in pavement | Metal mass detected overhead | Traffic junction controller | Schedules amber-to-red cycle transition |
| **5** | **Video Game** | Gamepad joystick / button | Button pressed (`Jump`) | Game input polling loop | Updates character velocity and plays jump audio |
| **6** | **Elevator System** | Hall call button (Floor 4) | Call button pressed | Dispatch algorithm supervisor | Dispatches nearest elevator car to Floor 4 |
| **7** | **Smartphone** | Capacitive touchscreen | Finger swipe gesture detected | Window touch dispatcher | Scrolls list view smoothly with physics inertia |
| **8** | **Supermarket Self-Checkout**| Barcode laser scanner | Barcode reflected laser scan | POS software application | Looks up SKU in database and adds item to basket |

### Activity 1 Self-Check Questions
1. **What is the difference between an event source and an event listener?**
   - An **event source** is the component or hardware sensor that detects a stimulus and generates an event object (e.g., a `JButton` or keyboard key).
   - An **event listener** is an object or interface implementation that registers interest in specific event types and waits to be notified when that event occurs.
2. **Can one event source have multiple listeners? Give an example.**
   - Yes. A single "Submit Order" button can have one listener that disables the button to prevent duplicate submissions, a second listener that validates form inputs, and a third listener that logs analytics data.
3. **Can one listener handle events from multiple sources? Give an example.**
   - Yes. A single central `ActionListener` can be attached to multiple buttons (e.g., `btnSave`, `btnCancel`, `btnHelp`) and inspect `e.getSource()` or `e.getActionCommand()` to decide which branch to execute.

---

## Activity 2 Deliverable: Event Loop Physical Simulation

### Reflection Paragraph
> The event queue acts as a synchronized First-In, First-Out (FIFO) buffer that temporarily stores pending user actions, timer triggers, and operating system messages as they occur asynchronously. The event loop runs continuously on a dedicated execution thread (such as Java Swing's Event Dispatch Thread), sequentially retrieving the next event from the head of the queue and routing it to the appropriate listener handler. Blocking the event loop with long-running tasksâ€”such as Thread.sleep, heavy computations, or network I/Oâ€”freezes the entire pipeline because subsequent events (including paint requests and mouse clicks) cannot be dequeued. In a real-world application, this results in an unresponsive UI, visual artifacts, and operating system "Not Responding" warnings. To maintain a responsive interface, heavy workloads must always be delegated to background threads, delivering only final visual updates back onto the event dispatch queue.

---

## Activity 3 Deliverable: Procedural vs. Event-Driven Comparison

### Comparison Table

| Aspect | Procedural Paradigm (`ProceduralCalculator.java`) | Event-Driven Paradigm (`EventDrivenCalculator.java`) |
| :--- | :--- | :--- |
| **Who controls the flow?** | The program logic rigidly dictates the exact sequence of prompts. | The user dictates the flow asynchronously by clicking buttons in any order. |
| **Error recovery** | Difficult; unexpected input at step 2 often aborts or crashes the program. | Easy; pressing 'C' (Clear) or entering a new digit instantly recovers the state. |
| **Interaction order** | Strict linear sequence: Number 1 â†’ Operator â†’ Number 2. | Non-linear: user can press buttons, change operators, or clear at any moment. |
| **Execution paths** | Few deterministic branches governed by conditionals and loops. | Virtually infinite combinations governed by user input events over time. |
| **Code organization** | Centralized, top-down procedure inside `main()`. | Decentralized; UI setup is decoupled into independent event handlers and lambdas. |
| **User experience** | Rigid console terminal requiring synchronous keyboard entry. | Fluid, intuitive graphical user interface (GUI) with immediate visual feedback. |

### Activity 3 Reflection Paragraph
> The procedural calculator enforces a rigid, linear control flow where the program remains in complete control and blocks execution while waiting for sequential console input. In contrast, the event-driven calculator relinquishes control to the user; the application initializes the user interface and then enters an idle state, responding dynamically whenever an event is dispatched. For interactive consumer applications, the event-driven approach is vastly superior because users expect freedom of navigation, immediate visual feedback, and the ability to correct mistakes without restarting the program. Event-driven programming is fundamental to modern graphical interfaces because it models real human-computer interaction, where stimuli occur unpredictably and multiple UI widgets must remain active and responsive simultaneously.

---

## Activity 4 Deliverable: Debugging Common EDP Mistakes

Refer to the full repair guide in [`BugFixChallenge/BugFixes.md`](BugFixChallenge/BugFixes.md):
- **Bug 1 (`Bug1_BlockingEDT.java`):** Offloaded 5-second sleep to a background thread to prevent UI freezing.
- **Bug 2 (`Bug2_MissingListener.java`):** Attached `ActionListener` to the increment button to handle click events.
- **Bug 3 (`Bug3_WrongThread.java`):** Dispatched clock label text changes through `SwingUtilities.invokeLater()`.
- **Bug 4 (`Bug4_MemoryLeak.java`):** Added `createdButtons.clear()` to garbage collect unreferenced components.

---

## Activity 5 Deliverable: System Design & Prototype

Refer to:
- [`SystemDesign.md`](SystemDesign.md) for complete event catalog, handler tables, and event flow architecture.
- [`SmartHomePrototype.java`](SmartHomePrototype.java) for the complete runnable Swing application.

---

## Activity 6: Final Self-Assessment

### Learning Outcome 1: Define EDP Fundamental Components

1. **What is an event? Give two examples.**
   - An **event** is an object representing a state change or stimulus that occurred in the system.
   - *Example 1:* An `ActionEvent` fired when a user clicks the "Calculate" button.
   - *Example 2:* A `KeyEvent` fired when the user presses the 'Escape' key.

2. **What is an event source? Give two examples.**
   - An **event source** is the object that detects the stimulus and creates/dispatches the event.
   - *Example 1:* A `JButton` instance.
   - *Example 2:* A `javax.swing.Timer` instance.

3. **What is an event listener? Give two examples.**
   - An **event listener** is an interface or object registered with a source to receive notifications when events occur.
   - *Example 1:* An `ActionListener` interface.
   - *Example 2:* A `MouseListener` interface.

4. **What is an event handler? Give two examples.**
   - An **event handler** is the specific method or callback body containing the logic executed in response to an event.
   - *Example 1:* The code inside `actionPerformed(ActionEvent e)`.
   - *Example 2:* A lambda expression `e -> display.setText("0")`.

5. **Relational Component Diagram:**
```
 â”Œâ”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”       Generates        â”Œâ”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”
 â”‚ EventSource â”‚ â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€> â”‚ EventObject â”‚
 â””â”€â”€â”€â”€â”€â”€â”¬â”€â”€â”€â”€â”€â”€â”˜                        â””â”€â”€â”€â”€â”€â”€â”¬â”€â”€â”€â”€â”€â”€â”˜
        â”‚                                      â”‚ Dispatched to
        â”‚ Registers                            â–¼
        â”‚                              â”Œâ”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”
        â””â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€> â”‚ EventListener â”‚
                                       â””â”€â”€â”€â”€â”€â”€â”€â”¬â”€â”€â”€â”€â”€â”€â”€â”˜
                                               â”‚ Invokes
                                               â–¼
                                       â”Œâ”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”
                                       â”‚ EventHandler  â”‚
                                       â”‚ (Logic / Code)â”‚
                                       â””â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”˜
```

---

### Learning Outcome 2: Compare Programming Paradigms

1. **How does procedural programming differ from event-driven programming?**
   - Procedural programming follows a top-down, step-by-step linear execution where the program controls the flow of time and halts for input. Event-driven programming is non-linear; the user or environment controls the sequence of events, and the system reacts to those events via registered handlers.

2. **What is the main advantage of event-driven programming for GUIs?**
   - It allows interfaces to remain fully interactive and responsive at all times, supporting arbitrary user interaction sequences, background data processing, and instantaneous visual state updates.

3. **In which scenario would you choose procedural programming over event-driven?**
   - Batch data processing scripts, compiler pipelines, data transformations, or sequential command-line math utilities where execution has a defined starting input, runs uninterrupted to completion, and requires no user interaction midway.

---

### Learning Outcome 3: Event Loop and Event Queue

1. **What is the event loop? What does it do?**
   - The event loop is a background process that continuously monitors the event queue. When an event is available, it pulls the event off the queue and executes its associated listener callback.

2. **What is the event queue? How does it work?**
   - The event queue is a First-In, First-Out (FIFO) data structure that buffers incoming events so none are lost while the processor or current handler is busy.

3. **Why should the event loop never be blocked?**
   - Because the event loop is single-threaded (the EDT in Swing). If a handler executes a long loop, sleeps, or waits for slow network I/O, no other events (clicks, keypresses, window repainting) can be processed, causing the entire GUI to freeze.

4. **What is the EDT in Java Swing?**
   - The **Event Dispatch Thread (EDT)** is the special background thread in Java Swing dedicated to processing GUI events, executing paint requests, and invoking event listener callbacks.

---

### Learning Outcome 4: Real-World Event-Driven Systems

1. **List five real-world event-driven systems:**
   - 1. Web browser DOM engine (click, scroll, network load)
   - 2. Modern automobile Engine Control Unit (throttle sensor, braking ABS trigger)
   - 3. Smart home alarm controller (motion sensors, door switches, keypad)
   - 4. Financial stock exchange trading engine (limit order submitted, price match event)
   - 5. Mobile smartphone operating system (push notifications, GPS location updates, touchscreen gestures)

2. **Four EDP components for the Smart Home Alarm:**
   - **Source:** Magnetic door contact sensor
   - **Event:** `DoorBreachEvent` (timestamp, sensor ID)
   - **Listener:** `AlarmZoneListener`
   - **Handler:** Sound siren, log timestamp, dispatch SMS notification to security dispatch

3. **Why are most modern applications event-driven?**
   - Modern systems operate in asynchronous, concurrent, and distributed environments where interactions (mouse clicks, network packets, sensor readings) arrive unpredictably. An event-driven architecture allows applications to decouple components, conserve resources when idle, and respond immediately to stimuli.

---

## Scoring Summary

| Question Category | Total Questions | Correct | Mastery Level |
| :--- | :---: | :---: | :--- |
| **Outcome 1: EDP Fundamental Components** | 5 | 5 | Mastered |
| **Outcome 2: Paradigm Comparisons** | 3 | 3 | Mastered |
| **Outcome 3: Event Loop & Queue** | 4 | 4 | Mastered |
| **Outcome 4: Real-World Systems** | 3 | 3 | Mastered |
| **Total Score** | **15** | **15** | **Mastered (13â€“15 Range)** |

---

## One-Page Summary of Week 1

```
â•”â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•—
â•‘                      WEEK 1 SUMMARY: EVENT-DRIVEN PROGRAMMING                â•‘
â•šâ•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•

1. THE FOUR CORE PILLARS:
   â€¢ Event: The message object containing data about what happened.
   â€¢ Event Source: The origin that generates the event (e.g., JButton).
   â€¢ Event Listener: The entity waiting for and registered to receive events.
   â€¢ Event Handler: The actual executable logic (e.g., ActionListener lambda).

2. ARCHITECTURAL LIFECYCLE:
   [User Action] â”€â”€> [Event Source] â”€â”€> [Event Queue (FIFO)]
                                                 â”‚
                                            [Event Loop] (EDT)
                                                 â”‚
                                                 â–¼
                                     [Event Listener / Handler]
                                                 â”‚
                                                 â–¼
                                          [UI State Update]

3. THREADING GOLDEN RULES (JAVA SWING):
   â€¢ Rule 1: NEVER block the Event Dispatch Thread (EDT). Long tasks belong on
             background threads (Thread / SwingWorker).
   â€¢ Rule 2: NEVER modify Swing components from outside the EDT. Always wrap UI
             mutations in SwingUtilities.invokeLater(() -> { ... }).
   â€¢ Rule 3: Always clean up listener references and collections to avoid
             memory leaks.
```
# Event-Driven Programming in Java

Hands-on practical activities, guides, and implementations for Event-Driven Programming (EDP), Observer Design Patterns, and Swing GUI development in Java.

---

## Documentation & Handbooks

Comprehensive self-paced student guides and theory notes are located in the [`documentation/`](documentation/) directory:
- [Week 1 Self-Paced Practical Activities](documentation/Week%201%20Self%20paced%20Activity.md)
- [Week 2 Self-Paced Observer Pattern Guide](documentation/Week%202%20Self%20paced%20Activity.md)
- [Week 3 Self-Paced GUI Application Guide](documentation/Week%203%20Self%20paced%20Activity.md)
- [Week 4 Interactive Swing Applications Guide](documentation/Week%204%20Self%20paced%20Activity.md)
- [Week 5 Advanced Swing Components Guide](documentation/Week%205%20Advanced%20Swing%20Components.md)
- [Week 6 Introduction to Modern JavaFX Guide](documentation/Week%206%20Introduction%20to%20Modern%20JavaFX.md)
- [Week 7 Self-Paced Event Handling in JavaFX](documentation/Week%207%20Self%20Paced%20Guide_Event%20Handling%20in%20JavaFX.md)

---

## Quick Start & General Run Instructions

### Prerequisites
- **Java Development Kit (JDK 11 or higher, recommended JDK 17/21/27)**
- Verify your installation with:
  ```powershell
  java -version
  javac -version
  ```

> [!IMPORTANT]
> ### 🎓 Instructor & Grader Quick-Run Guide
> To evaluate all student submissions quickly without terminal errors, use the commands matching your environment:
>
> 1. **Universal One-Command GUI Dashboard (Runs in ANY terminal on Windows/macOS/Linux):**
>    ```bash
>    java -cp week04-event-handling Week4Launcher
>    ```
>    *Opens the visual dashboard where you can launch any activity or click "Launch All 10 Activities Concurrently".*
>
> 2. **If You Are Using Git Bash (`MINGW64`) or macOS / Linux:**
>    *⚠️ DO NOT use Windows backslashes `.\scripts\run-week4.ps1` in Git Bash — backslashes are escape characters in Bash and will throw `bash: .scriptsrun-week4.ps1: command not found`.*
>    ```bash
>    ./scripts/run-week4.sh --all        # Launches all 10 Week 4 activities at once
>    ./scripts/run-week4.sh --launcher   # Opens the visual Master GUI Launcher
>    ./scripts/run-all.sh                # Main menu for all weeks (Weeks 1 to 5)
>    ```
>
> 3. **If You Are Using Windows PowerShell or CMD:**
>    *If script execution is disabled on your machine, use `.\agent-cli.bat` or bypass policy:*
>    ```powershell
>    .\scripts\run-week4.ps1 -All        # Launches all 10 Week 4 activities at once
>    .\scripts\run-week4.ps1 -Launcher   # Opens the visual Master GUI Launcher
>    .\scripts\run-all.ps1               # Main menu for all weeks (Weeks 1 to 5)
>    ```
>
> 4. **🤖 AI Agent CLI (Automated Grading, Headless Testing & JSON API):**
>    ```bash
>    ./agent-cli grade --week 4 --json   # Automated grading rubric (0–100 score)
>    ./agent-cli verify --week 4 --json  # Bytecode verification & thread safety audit
>    ./agent-cli list --week 4 --json    # Complete metadata schema for AI agents
>    ```

---

## 🔨 Project Build Pipeline (Like `npm run build`)

To ensure everything in the repository compiles cleanly and all 55+ source files and launchers are verified with zero errors, a centralized build pipeline is provided:

| Environment | Command | Clean & Rebuild |
| :--- | :--- | :--- |
| **Git Bash / Linux / macOS** | `./build` | `./build --clean` |
| **Windows CMD / PowerShell** | `.\build.bat` | `.\build.bat --clean` |
| **AI Agent CLI** | `./agent-cli build` | `./agent-cli build --clean --json` |
| **Node.js (if NPM is installed)** | `npm run build` | `npm run build:clean` |

**What the build pipeline does:**
1. Scans all 5 week directories (`Week1_Activity` through `week05-advanced-swing`) and launcher infrastructure.
2. If `--clean` is passed, deletes old `.class` artifacts.
3. Compiles all 55 source files simultaneously with `javac`.
4. Validates that 95+ `.class` bytecode files are generated properly.
5. Returns exit code `0` on success or non-zero on compiler errors with detailed diagnostics.

---

## AI Agent CLI (`agent-cli`)

A scriptable, headless, AI-agent-first CLI interface ([`scripts/AgentCLI.java`](scripts/AgentCLI.java)) designed for **LLM coding agents** (Antigravity, Claude Code, GitHub Copilot, Devin), **automated CI/CD pipelines**, and **headless grading bots**.

### Available Commands

| Command | Description | Example (Git Bash / PowerShell / CMD) |
| :--- | :--- | :--- |
| `info` | Host environment, JDK path, and coursework stats | `./agent-cli info --json` |
| `list` | Enumerate all 36 activities across Weeks 1–5 | `./agent-cli list --week 4 --json` |
| `verify` | Check source files, compiled bytecode, and EDT safety | `./agent-cli verify --week 4` |
| `inspect` | Static AST analysis of imports, lambdas, and patterns | `./agent-cli inspect w4-counter --json` |
| `grade` | Compute an automated evaluation score (0–100) & letter grade | `./agent-cli grade --week 4 --json` |
| `run` | Headless smoke testing (exits automatically after init) | `./agent-cli run w4-counter --headless --timeout 2000` |

> [!TIP]
> **Zero Dependencies:** The CLI is pure standard Java SE with zero Maven/Gradle overhead.
> - In Git Bash / Linux / macOS: use `./agent-cli <command> [options]`
> - In Windows PowerShell: use `.\agent-cli.bat <command> [options]`
> - In any environment: use `java -cp scripts AgentCLI <command> [options]`

We provide three convenient ways to run any activity:

#### 1. Interactive Scripts & GUI Launchers (Organized in `scripts/` — Recommended)
All execution scripts are maintained inside the [`scripts/`](scripts/) folder to keep the repository root clean.

##### **For Git Bash / Linux / macOS (`.sh`):**
```bash
./scripts/run-all.sh            # Main interactive menu for all weeks (1 to 5)
./scripts/run-week4.sh          # Week 4 interactive menu
./scripts/run-week4.sh --all    # Launch all 10 Week 4 activities concurrently
./scripts/run-week4.sh --launcher # Open Week 4 Master GUI Dashboard
```

##### **For Windows PowerShell (`.ps1`):**
```powershell
.\scripts\run-all.ps1           # Main interactive menu for all weeks (1 to 5)
.\scripts\run-week4.ps1         # Week 4 interactive menu
.\scripts\run-week4.ps1 -All    # Launch all 10 Week 4 activities concurrently
.\scripts\run-week4.ps1 -Launcher # Open Week 4 Master GUI Dashboard
```

| Week / Scope | Git Bash (`.sh`) | PowerShell (`.ps1`) | Run All Concurrently | Master GUI Dashboard |
| :--- | :--- | :--- | :--- | :--- |
| **All Weeks (1–5)** | `./scripts/run-all.sh` | `.\scripts\run-all.ps1` | Interactive Menu | Direct dashboard shortcuts |
| **Week 1** | `./scripts/run-week1.sh` | `.\scripts\run-week1.ps1` | `--all` / `-All` | Single activity selector |
| **Week 2** | `./scripts/run-week2.sh` | `.\scripts\run-week2.ps1` | `--all` / `-All` | `--launcher` / `-Launcher` |
| **Week 3** | `./scripts/run-week3.sh` | `.\scripts\run-week3.ps1` | `--all` / `-All` | `--launcher` / `-Launcher` |
| **Week 4** | `./scripts/run-week4.sh` | `.\scripts\run-week4.ps1` | `--all` / `-All` | `--launcher` / `-Launcher` |
| **Week 5** | `./scripts/run-week5.sh` | `.\scripts\run-week5.ps1` | `--all` / `-All` | `--launcher` / `-Launcher` |

*(You can also launch any Master GUI Dashboard directly with Java: `java -cp Week02-observer-pattern Week2Launcher`, `java -cp week03-swing-gui Week3Launcher`, `java -cp week04-event-handling Week4Launcher`, or `java -cp week05-advanced-swing Week5Launcher`)*

#### 2. From the Repository Root Directory (Classpath `-cp`)
```powershell
javac <path/to/folder>/*.java
java -cp <path/to/folder> <MainClassName>
```

#### 3. From Inside the Specific Activity Directory
```powershell
cd <path/to/folder>
javac *.java
java <MainClassName>
```

---

## Detailed Activity Run Guide

### Week 1: Fundamentals of Event-Driven Programming (`Week1_Activity/`)

Detailed design notes and bug-fix analyses are in [Week1_Activity/SelfAssessment.md](Week1_Activity/SelfAssessment.md), [Week1_Activity/SystemDesign.md](Week1_Activity/SystemDesign.md), and [Week1_Activity/BugFixChallenge/BugFixes.md](Week1_Activity/BugFixChallenge/BugFixes.md).

#### Quick Runners
```powershell
.\scripts\run-week1.ps1           # Interactive menu for all Week 1 activities
.\scripts\run-week1.ps1 -All      # Run all activities (CLI + GUI)
.\scripts\run-week1.ps1 -Activity 2  # Run EventDrivenCalculator
```

#### Activity 3: Procedural vs. Event-Driven Comparison
Compare synchronous procedural execution vs. asynchronous GUI event loop.

- **Subactivity 3.1: Procedural Console Calculator (CLI)**
  ```powershell
  # Root:
  javac Week1_Activity/ProceduralCalculator.java
  java -cp Week1_Activity ProceduralCalculator
  ```
- **Subactivity 3.2: Event-Driven Swing Calculator (GUI)**
  ```powershell
  # Root:
  javac Week1_Activity/EventDrivenCalculator.java
  java -cp Week1_Activity EventDrivenCalculator
  ```

#### Activity 4: Bug Fix Challenge (`Week1_Activity/BugFixChallenge/`)
Practical bug-fixing exercises demonstrating common event-driven pitfalls and solutions.

- **Bug 1: Blocking the Event Dispatch Thread (EDT)**
  ```powershell
  javac Week1_Activity/BugFixChallenge/Bug1_BlockingEDT.java
  java -cp Week1_Activity/BugFixChallenge Bug1_BlockingEDT
  ```
- **Bug 2: Missing Event Listener**
  ```powershell
  javac Week1_Activity/BugFixChallenge/Bug2_MissingListener.java
  java -cp Week1_Activity/BugFixChallenge Bug2_MissingListener
  ```
- **Bug 3: Mutating UI from Background Thread**
  ```powershell
  javac Week1_Activity/BugFixChallenge/Bug3_WrongThread.java
  java -cp Week1_Activity/BugFixChallenge Bug3_WrongThread
  ```
- **Bug 4: Memory Leak from Dangling Listeners**
  ```powershell
  javac Week1_Activity/BugFixChallenge/Bug4_MemoryLeak.java
  java -cp Week1_Activity/BugFixChallenge Bug4_MemoryLeak
  ```

#### Activity 5: Smart Home Event-Driven System Prototype
Interactive Swing prototype demonstrating sensors, event dispatchers, and stateful listeners.

```powershell
# Root:
javac Week1_Activity/SmartHomePrototype.java
java -cp Week1_Activity SmartHomePrototype
```

---

### Week 2: The Observer Pattern (`Week02-observer-pattern/`)

Covers decoupling subjects and observers, pull vs. push data delivery, and Java Bean property change notifications. Detailed write-ups are in [Week02-observer-pattern/SelfAssessment.md](Week02-observer-pattern/SelfAssessment.md), [documentation/Week 2 Self paced Activity.md](documentation/Week%202%20Self%20paced%20Activity.md), and [Week02-observer-pattern/activity4-comparison/Comparison.md](Week02-observer-pattern/activity4-comparison/Comparison.md).

#### Quick Master Runners
- **Run the Master GUI Launcher (runs simulations with live console output):**
  ```powershell
  java -cp Week02-observer-pattern Week2Launcher
  ```
- **Run with Bash (`.sh`) or PowerShell (`.ps1`) script in `scripts/`:**
  ```bash
  ./scripts/run-week2.sh --launcher   # Git Bash / Linux
  .\scripts\run-week2.ps1 -Launcher   # Windows PowerShell
  .\scripts\run-week2.ps1 -All        # Run all observer pattern console demos
  ```

#### Activity 1: Weather Station Simulation
Demonstrates the classic GoF Observer pattern with dynamic observer registration, state updates, and deregistration.

```powershell
# Root:
javac Week02-observer-pattern/activity1-weather-station/*.java
java -cp Week02-observer-pattern/activity1-weather-station WeatherStationMain
```

#### Activity 2: Legacy `java.util.Observer` Implementation
Demonstrates how the historical Java observer API operated prior to being deprecated in Java 9.

```powershell
# Root:
javac Week02-observer-pattern/activity2-legacy-observer/*.java
java -cp Week02-observer-pattern/activity2-legacy-observer LegacyMain
```

#### Activity 3: Custom Observer & Decoupling
Compares tightly coupled architectures against a clean decoupled observer implementation.

- **Subactivity 3.1: Tightly Coupled System (Anti-Pattern Baseline)**
  ```powershell
  javac Week02-observer-pattern/activity3-custom-observer/TightlyCoupledSystem.java
  java -cp Week02-observer-pattern/activity3-custom-observer TightlyCoupledSystem
  ```
- **Subactivity 3.2: Refactored Observer Pattern Implementation**
  ```powershell
  javac Week02-observer-pattern/activity3-custom-observer/*.java
  java -cp Week02-observer-pattern/activity3-custom-observer RefactoredMain
  ```

#### Activity 4: `PropertyChangeListener` Modern Alternative
Modern alternative using `java.beans.PropertyChangeSupport` and `PropertyChangeEvent`.

```powershell
# Root:
javac Week02-observer-pattern/activity4-comparison/PropertyChangeDemo.java
java -cp Week02-observer-pattern/activity4-comparison PropertyChangeDemo
```

---

### Week 3: Building GUI Applications with Swing (`week03-swing-gui/`)

Covers Swing top-level containers (`JFrame`), core widgets, layout manager algorithms, thread safety rules for the Event Dispatch Thread (EDT), and UI mockups. Detailed report is in [week03-swing-gui/SelfAssessment.md](week03-swing-gui/SelfAssessment.md).

#### Quick Master Runners
- **Run the Master GUI Launcher (runs all activities from one dashboard):**
  ```powershell
  java -cp week03-swing-gui Week3Launcher
  ```
- **Run with PowerShell script in `scripts/` (interactive menu or launch all at once):**
  ```powershell
  .\scripts\run-week3.ps1           # Interactive menu
  .\scripts\run-week3.ps1 -All      # Launches all 9 activities simultaneously
  .\scripts\run-week3.ps1 -Launcher # Opens Master GUI Launcher
  ```

#### Activity 1: First Swing Window
Creates a centered `JFrame` configured on the EDT via `SwingUtilities.invokeLater()`.

```powershell
# Root:
javac week03-swing-gui/activity1-first-window/FirstWindow.java
java -cp week03-swing-gui/activity1-first-window FirstWindow
```

#### Activity 2: Component Showcase
Showcases major Swing components (`JLabel`, `JTextField`, `JPasswordField`, `JCheckBox`, `JRadioButton`, `JComboBox`, `JTextArea`, `JButton`).

```powershell
# Root:
javac week03-swing-gui/activity2-basic-components/ComponentShowcase.java
java -cp week03-swing-gui/activity2-basic-components ComponentShowcase
```

#### Activity 3: Layout Managers Deep Dive
Demonstrates how containers calculate size and arrange components.

- **Subactivity 3.1: FlowLayout Demo**
  ```powershell
  javac week03-swing-gui/activity3-layout-managers/FlowLayoutDemo.java
  java -cp week03-swing-gui/activity3-layout-managers FlowLayoutDemo
  ```
- **Subactivity 3.2: BorderLayout Demo**
  ```powershell
  javac week03-swing-gui/activity3-layout-managers/BorderLayoutDemo.java
  java -cp week03-swing-gui/activity3-layout-managers BorderLayoutDemo
  ```
- **Subactivity 3.3: GridLayout Demo**
  ```powershell
  javac week03-swing-gui/activity3-layout-managers/GridLayoutDemo.java
  java -cp week03-swing-gui/activity3-layout-managers GridLayoutDemo
  ```
- **Subactivity 3.4: Combined & Nested Layout Demo**
  ```powershell
  javac week03-swing-gui/activity3-layout-managers/CombinedLayoutDemo.java
  java -cp week03-swing-gui/activity3-layout-managers CombinedLayoutDemo
  ```

#### Activity 4: EDT Rule Verification
Demonstrates why background workers are essential for non-freezing user interfaces.

- **Subactivity 4.1: Freeze Demo (EDT Blocking Antipattern)**
  ```powershell
  javac week03-swing-gui/activity4-edt-rule/FreezeDemo.java
  java -cp week03-swing-gui/activity4-edt-rule FreezeDemo
  ```
- **Subactivity 4.2: Fixed Demo (Thread-Safe Asynchronous Worker)**
  ```powershell
  javac week03-swing-gui/activity4-edt-rule/FixedDemo.java
  java -cp week03-swing-gui/activity4-edt-rule FixedDemo
  ```

#### Activity 5: Complete Student Portal GUI Mockup
Full dashboard mockup with custom rounded panels, vector icons, navigation sidebar, metric cards, and notification feed.

```powershell
# Root:
javac week03-swing-gui/activity5-complete-mockup/StudentPortal.java
java -cp week03-swing-gui/activity5-complete-mockup StudentPortal
```

---

### Week 4: Event Handling in Swing (`week04-event-handling/`)

Covers listener styles (`ActionListener`, `ItemListener`, `DocumentListener`), event source inspection, keyboard shortcuts (`KeyStroke` & `ActionMap`), comprehensive form validation, and low-level mouse/keyboard adapters. Detailed assessment is in [week04-event-handling/SelfAssessment.md](week04-event-handling/SelfAssessment.md).

#### Quick Master Runners
- **Run the Master GUI Launcher (runs all activities from one dashboard):**
  ```powershell
  java -cp week04-event-handling Week4Launcher
  ```
- **Run with PowerShell script in `scripts/` (interactive menu or launch all at once):**
  ```powershell
  .\scripts\run-week4.ps1           # Interactive menu
  .\scripts\run-week4.ps1 -All      # Launches all 10 activities simultaneously
  .\scripts\run-week4.ps1 -Launcher # Opens Master GUI Launcher
  ```

#### Activity 1: ActionListener Basics
Simple button interaction with popup dialog via lambda `ActionListener`.

```powershell
javac week04-event-handling/activity1-actionlistener-basics/FirstButton.java
java -cp week04-event-handling/activity1-actionlistener-basics FirstButton
```

#### Activity 2: Listener Techniques & Event Sources
Comparing named class, anonymous inner class, lambda, and method reference, plus detecting components with `e.getSource()`.

- **Subactivity 2.1: Listener Implementation Techniques**
  ```powershell
  javac week04-event-handling/activity2-listener-techniques/ListenerDemo.java
  java -cp week04-event-handling/activity2-listener-techniques ListenerDemo
  ```
- **Subactivity 2.2: Event Source Identification**
  ```powershell
  javac week04-event-handling/activity2-listener-techniques/EventSourceDemo.java
  java -cp week04-event-handling/activity2-listener-techniques EventSourceDemo
  ```

#### Activity 3: Item and Document Listeners
Handling state toggles in checkboxes/radio buttons and real-time keystroke tracking in text fields.

- **Subactivity 3.1: Pizza Order Form (`ItemListener`)**
  ```powershell
  javac week04-event-handling/activity3-item-and-document-listeners/ItemListenerDemo.java
  java -cp week04-event-handling/activity3-item-and-document-listeners ItemListenerDemo
  ```
- **Subactivity 3.2: Live Character Counter (`DocumentListener`)**
  ```powershell
  javac week04-event-handling/activity3-item-and-document-listeners/DocumentListenerDemo.java
  java -cp week04-event-handling/activity3-item-and-document-listeners DocumentListenerDemo
  ```
- **Subactivity 3.3: Live Search Filtering (`DocumentListener`)**
  ```powershell
  javac week04-event-handling/activity3-item-and-document-listeners/LiveSearchDemo.java
  java -cp week04-event-handling/activity3-item-and-document-listeners LiveSearchDemo
  ```

#### Activity 4: Counter Application with Keyboard Shortcuts
State-managed counter with visual threshold alerts (0–20 limits) and window-level keyboard bindings (`UP`, `DOWN`, `R`).

```powershell
javac week04-event-handling/activity4-counter-app/CounterApp.java
java -cp week04-event-handling/activity4-counter-app CounterApp
```

#### Activity 5: Interactive Registration Form
Multi-field form with field validation (name, email format, password length), selection groups, reset logic, and confirmation dialogs.

```powershell
javac week04-event-handling/activity5-interactive-form/RegistrationForm.java
java -cp week04-event-handling/activity5-interactive-form RegistrationForm
```

#### Activity 6: Mouse and Key Event Handling
Low-level input processing using `MouseAdapter` and `KeyAdapter`.

- **Subactivity 6.1: Freehand Drawing Canvas (`MouseAdapter`)**
  ```powershell
  javac week04-event-handling/activity6-mouse-and-key/DrawingApp.java
  java -cp week04-event-handling/activity6-mouse-and-key DrawingApp
  ```
- **Subactivity 6.2: Ball Movement with Arrow Keys (`KeyAdapter`)**
  ```powershell
  javac week04-event-handling/activity6-mouse-and-key/KeyboardMover.java
  java -cp week04-event-handling/activity6-mouse-and-key KeyboardMover
  ```

---

### Week 5: Advanced Swing Components & Multi-Pane Applications (`week05-advanced-swing/`)

Covers advanced data components (`JList`, `JTable`, `JTree`, `JTabbedPane`), custom cell renderers, menus, toolbars, file choosers, modal dialogs, and multi-pane split architectures. Detailed assessment is in [week05-advanced-swing/SelfAssessment.md](week05-advanced-swing/SelfAssessment.md).

#### Quick Master Runners
- **Run the Master GUI Launcher (runs all activities from one dashboard):**
  ```powershell
  java -cp week05-advanced-swing Week5Launcher
  ```
- **Run with PowerShell script in `scripts/` (interactive menu or launch all at once):**
  ```powershell
  .\scripts\run-week5.ps1           # Interactive menu
  .\scripts\run-week5.ps1 -All      # Launches all 5 activities simultaneously
  .\scripts\run-week5.ps1 -Launcher # Opens Master GUI Launcher
  ```

#### Activity 1: JList and Custom Renderers
Contact list featuring a custom `ListCellRenderer` with status dot icons (Online, Away, Offline) and selection state listener.

```powershell
javac week05-advanced-swing/activity1-jlist-renderer/ContactListDemo.java
java -cp week05-advanced-swing/activity1-jlist-renderer ContactListDemo
```

#### Activity 2: JTable Tabular Data Display
Student database table with `DefaultTableModel`, column-level editability constraints, custom GPA cell rendering (< 3.0 in red), and CRUD actions (Add, Delete Selected, Clear All).

```powershell
javac week05-advanced-swing/activity2-jtable/StudentTableDemo.java
java -cp week05-advanced-swing/activity2-jtable StudentTableDemo
```

#### Activity 3: JTree and JTabbedPane
Explorer interface combining hierarchical tree navigation (`JTree`), tabbed settings panels (`JTabbedPane`), and a resizable horizontal split layout (`JSplitPane`).

```powershell
javac week05-advanced-swing/activity3-jtree-tabs/FileExplorerDemo.java
java -cp week05-advanced-swing/activity3-jtree-tabs FileExplorerDemo
```

#### Activity 4: Menus, Toolbars, and Dialogs (MiniEditor)
Desktop text editor featuring application menus (`JMenuBar`, `JMenu`, `JMenuItem`), quick-access toolbar (`JToolBar`), real-time word counting, and native file operations via `JFileChooser`.

```powershell
javac week05-advanced-swing/activity4-menus-dialogs/MiniEditor.java
java -cp week05-advanced-swing/activity4-menus-dialogs MiniEditor
```

#### Activity 5: Interactive File Manager Capstone
Full desktop file manager integrating `JTree` directory traversal, `JTable` file details, `JSplitPane`, toolbar navigation (Home, Up, Refresh), and double-click folder drilling.

```powershell
javac week05-advanced-swing/activity5-capstone-file-manager/FileManager.java
java -cp week05-advanced-swing/activity5-capstone-file-manager FileManager
```

---

## ⚡ Week 6: Introduction to Modern JavaFX (NetBeans Edition)

A complete modern JavaFX Maven project ([`week06-javafx/`](week06-javafx/)) demonstrating core JavaFX architecture (`Stage`, `Scene`, `Node`), programmatic layouts (`HBox`, `VBox`, `GridPane`, `BorderPane`), declarative FXML visual UI design using Scene Builder, Model-View-Controller (MVC) wiring with `@FXML` dependency injection, and a production-grade Capstone SaaS Dashboard.

### Quick Master Runners
- **Run the Master GUI Launcher (runs all Week 6 activities from one dashboard):**
  ```powershell
  java -cp week06-javafx Week6Launcher
  ```
- **Run with PowerShell script in `scripts/` (interactive menu or launch all at once):**
  ```powershell
  .\scripts\run-week6.ps1           # Interactive menu
  .\scripts\run-week6.ps1 -All      # Launches all activities simultaneously
  .\scripts\run-week6.ps1 -Launcher # Opens Master GUI Launcher
  ```
- **Run with Bash script in `scripts/` (Git Bash & Linux):**
  ```bash
  ./scripts/run-week6.sh --launcher
  ./scripts/run-week6.sh --all
  ```

### Activities & Implementations

#### Activity 1: Your First JavaFX Window (Programmatic)
Constructs a JavaFX application entirely in code to understand the core architecture: `Stage` (window), `Scene` (container), and `Node` (components), running on the dedicated **`JavaFX Application Thread`**.
- Source: [`week06-javafx/src/main/java/programmaticdemo/Main.java`](week06-javafx/src/main/java/programmaticdemo/Main.java)
```powershell
# Maven run:
cd week06-javafx && .\mvnw.cmd javafx:run -Djavafx.mainClass=programmaticdemo.Main
# Direct run:
java --module-path week06-javafx/lib/win --add-modules javafx.controls,javafx.fxml -cp week06-javafx/target/classes programmaticdemo.Main
```

#### Activity 2: JavaFX Layouts (Programmatic)
Arranges UI components using JavaFX layout panes: a dark `HBox` top bar, a vertical `VBox` sidebar, and a 3×3 `GridPane` of interactive buttons, all assembled inside a master `BorderPane`.
- Source: [`week06-javafx/src/main/java/programmaticdemo/LayoutDemo.java`](week06-javafx/src/main/java/programmaticdemo/LayoutDemo.java)
```powershell
cd week06-javafx && .\mvnw.cmd javafx:run -Djavafx.mainClass=programmaticdemo.LayoutDemo
```

#### Activity 3: Visual UI Design with Scene Builder & FXML
Declarative XML-based UI layout built with Scene Builder. Defines a centered `VBox` containing header label, `TextField` (`usernameField`), `PasswordField` (`passwordField`), and `Button` (`loginButton`).
- FXML View: [`week06-javafx/src/main/resources/programmaticdemo/login.fxml`](week06-javafx/src/main/resources/programmaticdemo/login.fxml)

#### Activity 4: Wiring FXML with a Controller
Connects the visual FXML view to Java logic following the Model-View-Controller (MVC) pattern. Leverages `@FXML` field injection and `#handleLoginButtonAction` to validate credentials (`admin` / `password123`) and log thread safety details.
- Controller: [`week06-javafx/src/main/java/programmaticdemo/LoginController.java`](week06-javafx/src/main/java/programmaticdemo/LoginController.java)
- Application Entry: [`week06-javafx/src/main/java/programmaticdemo/FXMLApp.java`](week06-javafx/src/main/java/programmaticdemo/FXMLApp.java)
```powershell
cd week06-javafx && .\mvnw.cmd javafx:run -Djavafx.mainClass=programmaticdemo.FXMLApp
```

#### Activity 5: Capstone — Modern SaaS Dashboard
Simulates an enterprise SaaS application (AWS, Stripe, Azure) layout featuring a dark top bar (`#2c3e50`), navigation sidebar (`#ecf0f1`), and a responsive center workspace showcasing modular color-coded metric cards for Revenue, Users, and Orders.
- Source: [`week06-javafx/src/main/java/programmaticdemo/DashboardApp.java`](week06-javafx/src/main/java/programmaticdemo/DashboardApp.java)
```powershell
cd week06-javafx && .\mvnw.cmd javafx:run -Djavafx.mainClass=programmaticdemo.DashboardApp
```

---

## 🎨 Week 7: Event Handling in JavaFX

A complete modern JavaFX Maven project ([`week07-javafx-events/`](week07-javafx-events/)) demonstrating event filters, bubbling event handlers, real-time mouse coordinate tracking, focus management, keyboard arrow motion, interactive drag-and-drop transfer pipelines, and decoupled custom events.

### Activities & Implementations

#### Activity 1: Event Propagation (Filters vs. Handlers)
Demonstrates the capturing phase (top-down event filter on root node) vs. bubbling phase (bottom-up event handler on root and target button), including event consumption via `e.consume()`.
- Source: [`week07-javafx-events/src/main/java/com/example/EventPropagationDemo.java`](week07-javafx-events/src/main/java/com/example/EventPropagationDemo.java)
```bash
mvn -f week07-javafx-events/pom.xml javafx:run -Djavafx.mainClass=com.example.EventPropagationDemo
```

#### Activity 2: Mouse and Key Events
Real-time tracking of mouse pointer X/Y coordinates via `setOnMouseMoved()` and keyboard-driven character movement via `setOnKeyPressed()`, `KeyCode`, and boundary collision clamping.
- Source: [`week07-javafx-events/src/main/java/com/example/MouseKeyDemo.java`](week07-javafx-events/src/main/java/com/example/MouseKeyDemo.java)
```bash
mvn -f week07-javafx-events/pom.xml javafx:run -Djavafx.mainClass=com.example.MouseKeyDemo
```

#### Activity 3: Drag and Drop Interaction
Complete interactive drag-and-drop workflow: drag initiation from source label (`setOnDragDetected`, `TransferMode.MOVE`, `ClipboardContent`), hover detection and visual drop target feedback (`setOnDragOver`, `setOnDragEntered`, `setOnDragExited`), and completion (`setOnDragDropped`, `setDropCompleted`).
- Source: [`week07-javafx-events/src/main/java/com/example/DragDropDemo.java`](week07-javafx-events/src/main/java/com/example/DragDropDemo.java)
```bash
mvn -f week07-javafx-events/pom.xml javafx:run -Djavafx.mainClass=com.example.DragDropDemo
```

#### Activity 4: Custom Events & Loose Coupling
Decoupled enterprise architecture using a custom typed event `LoginSuccessEvent` extending `javafx.event.Event`, fired via `root.fireEvent()` and handled independently by decoupled consumers via `root.addEventHandler()`.
- Source: [`week07-javafx-events/src/main/java/com/example/LoginSuccessEvent.java`](week07-javafx-events/src/main/java/com/example/LoginSuccessEvent.java) & [`week07-javafx-events/src/main/java/com/example/CustomEventDemo.java`](week07-javafx-events/src/main/java/com/example/CustomEventDemo.java)
```bash
mvn -f week07-javafx-events/pom.xml javafx:run -Djavafx.mainClass=com.example.CustomEventDemo
```

#### Master JavaFX Dashboard
Single-click graphical dashboard with cards for each activity and concurrent multi-window launching.
- Source: [`week07-javafx-events/src/main/java/com/example/Week7Launcher.java`](week07-javafx-events/src/main/java/com/example/Week7Launcher.java)
```bash
mvn -f week07-javafx-events/pom.xml javafx:run
```

---

## Batch Compilation Commands

To compile all `.java` source files in the entire project at once:

```powershell
# Compile all source files across all weeks at once (PowerShell):
javac (Get-ChildItem -Filter *.java -Recurse | Select-Object -ExpandProperty FullName)

# Or compile Week 5 specifically:
javac (Get-ChildItem -Path week05-advanced-swing -Filter *.java -Recurse | Select-Object -ExpandProperty FullName)

# Or compile Week 4 specifically:
javac (Get-ChildItem -Path week04-event-handling -Filter *.java -Recurse | Select-Object -ExpandProperty FullName)
```

---

## Centralized Automation & Master Launchers Architecture

To keep the repository clean, structured, and easy to run across all modules, dedicated automation and launcher layers have been added:

### 1. Organized Scripts Directory (`scripts/`)
All runner scripts are consolidated in [`scripts/`](scripts/) to prevent root-directory clutter:
- **[`scripts/run-all.ps1`](scripts/run-all.ps1)**: Interactive hub dispatcher to explore or launch any activity from Week 1 through Week 7.
- **[`scripts/run-week1.ps1`](scripts/run-week1.ps1)**: CLI & GUI launcher with interactive menu, `-All` flag, and `-Activity <id>`.
- **[`scripts/run-week2.ps1`](scripts/run-week2.ps1)**: Observer pattern test runner executing console simulations in non-closing windows.
- **[`scripts/run-week3.ps1`](scripts/run-week3.ps1)**: Controls all 9 layout and EDT activities with `-Launcher` GUI shortcut.
- **[`scripts/run-week4.ps1`](scripts/run-week4.ps1)**: Controls all 10 event-handling activities with `-Launcher` GUI shortcut.
- **[`scripts/run-week5.ps1`](scripts/run-week5.ps1)**: Controls all 5 advanced Swing activities with `-Launcher` GUI shortcut.
- **[`scripts/run-week6.ps1`](scripts/run-week6.ps1)**: Controls all 5 modern JavaFX activities with `-Launcher` GUI shortcut.
- **[`scripts/run-week7.ps1`](scripts/run-week7.ps1)**: Controls all 4 JavaFX event activities with `-Launcher` GUI shortcut.

### 2. Standalone Master GUI Dashboards
Built for Week 2, Week 3, Week 4, Week 5, Week 6, and Week 7, these dashboards allow single-click execution or concurrent launching of every activity:
- **[`Week02-observer-pattern/Week2Launcher.java`](Week02-observer-pattern/Week2Launcher.java)** (Interactive dashboard with live console output stream)
- **[`week03-swing-gui/Week3Launcher.java`](week03-swing-gui/Week3Launcher.java)**
- **[`week04-event-handling/Week4Launcher.java`](week04-event-handling/Week4Launcher.java)**
- **[`week05-advanced-swing/Week5Launcher.java`](week05-advanced-swing/Week5Launcher.java)**
- **[`week06-javafx/Week6Launcher.java`](week06-javafx/Week6Launcher.java)**
- **[`week07-javafx-events/src/main/java/com/example/Week7Launcher.java`](week07-javafx-events/src/main/java/com/example/Week7Launcher.java)**

### 3. Process Isolation & Safety
- Each application launched through the GUI dashboards or PowerShell scripts runs as an independent OS process via Java's `ProcessBuilder` or PowerShell's `Start-Process`.
- This ensures that when a student or instructor closes any window containing `frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)`, only that specific activity terminates without closing the dashboard or sibling activity windows.
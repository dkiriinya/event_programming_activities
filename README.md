# Event-Driven Programming in Java

Hands-on practical activities, guides, and implementations for Event-Driven Programming (EDP), Observer Design Patterns, and Swing GUI development in Java.

---

## Documentation & Handbooks

Comprehensive self-paced student guides and theory notes are located in the [`documentation/`](documentation/) directory:
- [Week 1 Self-Paced Practical Activities](documentation/Week%201%20Self%20paced%20Activity.md)
- [Week 3 Self-Paced GUI Application Guide](documentation/Week%203%20Self%20paced%20Activity.md)
- [Week 4 Interactive Swing Applications Guide](documentation/Week%204%20Self%20paced%20Activity.md)
- [Week 5 Advanced Swing Components Guide](documentation/Week%205%20Advanced%20Swing%20Components.md)

---

## Quick Start & General Run Instructions

### Prerequisites
- **Java Development Kit (JDK 11 or higher, recommended JDK 17/21/27)**
- Verify your installation with:
  ```powershell
  java -version
  javac -version
  ```

### How to Run Activities
You can run any activity either:
1. **From the repository root directory** (recommended) using `-cp` (classpath):
   ```powershell
   javac <path/to/folder>/*.java
   java -cp <path/to/folder> <MainClassName>
   ```
2. **From inside the specific activity directory**:
   ```powershell
   cd <path/to/folder>
   javac *.java
   java <MainClassName>
   ```

---

## Detailed Activity Run Guide

### Week 1: Fundamentals of Event-Driven Programming (`Week1_Activity/`)

Detailed design notes and bug-fix analyses are in [Week1_Activity/SelfAssessment.md](Week1_Activity/SelfAssessment.md), [Week1_Activity/SystemDesign.md](Week1_Activity/SystemDesign.md), and [Week1_Activity/BugFixChallenge/BugFixes.md](Week1_Activity/BugFixChallenge/BugFixes.md).

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

Covers decoupling subjects and observers, pull vs. push data delivery, and Java Bean property change notifications. Detailed write-ups are in [Week02-observer-pattern/SelfAssessment.md](Week02-observer-pattern/SelfAssessment.md) and [Week02-observer-pattern/activity4-comparison/Comparison.md](Week02-observer-pattern/activity4-comparison/Comparison.md).

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
# Week 6 — Introduction to Modern JavaFX (NetBeans Edition): Self-Assessment & Activity Report

---

## Executive Summary & Deliverables Checklist

- [x] **Activity 1: Your First JavaFX Window (Programmatic)** — [`src/main/java/programmaticdemo/Main.java`](src/main/java/programmaticdemo/Main.java)
  - Constructed core JavaFX architecture: `Stage`, `Scene`, and `Node` hierarchy entirely in code.
  - Implemented event handling via lambda on `clickButton.setOnAction(...)`.
  - Verified UI dispatch on the **`JavaFX Application Thread`** (analogous to Swing's EDT).
  - Applied CSS inline styling (`-fx-alignment`, `-fx-padding`, `-fx-background-color`).
- [x] **Activity 2: JavaFX Layouts (Programmatic)** — [`src/main/java/programmaticdemo/LayoutDemo.java`](src/main/java/programmaticdemo/LayoutDemo.java)
  - Assembled multi-region layout combining `HBox` (top bar), `VBox` (sidebar), and `GridPane` (3×3 button matrix).
  - Orchestrated layout panes within a master `BorderPane` (Top, Left, Center).
  - Verified dynamic resizing and alignment behavior across window dimension changes.
- [x] **Activity 3: Visual UI Design with Scene Builder & FXML** — [`src/main/resources/programmaticdemo/login.fxml`](src/main/resources/programmaticdemo/login.fxml)
  - Designed declarative XML user interface using Scene Builder.
  - Built centered `VBox` containing header `Label`, `TextField` (`usernameField`), `PasswordField` (`passwordField`), and `Button` (`loginButton`).
  - Linked FXML layout to `programmaticdemo.LoginController` via `fx:controller`.
- [x] **Activity 4: Wiring FXML with a Controller** — [`src/main/java/programmaticdemo/LoginController.java`](src/main/java/programmaticdemo/LoginController.java) & [`src/main/java/programmaticdemo/FXMLApp.java`](src/main/java/programmaticdemo/FXMLApp.java)
  - Connected visual view to Java logic using `@FXML` field injection and `#handleLoginButtonAction` handler.
  - Implemented credential validation (`admin` / `password123`) with empty-field error guard and field clearing.
  - Created standalone application entry point loading FXML via `FXMLLoader.load()`.
- [x] **Activity 5: Capstone — Modern SaaS Dashboard** — [`src/main/java/programmaticdemo/DashboardApp.java`](src/main/java/programmaticdemo/DashboardApp.java)
  - Engineered production-grade SaaS desktop interface modeling AWS, Stripe, and Azure dashboards.
  - Implemented dark header bar (`#2c3e50`), navigation sidebar (`#ecf0f1`) with action buttons, and center workspace.
  - Developed reusable `createCard(title, value, color)` helper generating curved metric cards (Revenue, Users, Orders).
- [x] **Master Activity Launcher** — [`Week6Launcher.java`](Week6Launcher.java)
  - Built unified Swing/JavaFX launcher dashboard allowing single-click or concurrent execution of all Week 6 activities in process-isolated environments.
- [x] **Maven & NetBeans Integration** — [`pom.xml`](pom.xml)
  - Integrated Maven build configuration with `javafx-controls` and `javafx-fxml` dependencies (OpenJFX 21).
  - Configured `javafx-maven-plugin` and embedded Maven Wrapper (`mvnw` / `mvnw.cmd`).

---

## Learning Outcomes Self-Assessment

### Learning Outcome 1: Create a Basic JavaFX Application
- **Self-Rating:** 5 / 5
- **Core Architecture Analysis:**
  - **`Application`**: Abstract entry class of every JavaFX program. Lifecyle methods: `init()`, `start(Stage primaryStage)`, and `stop()`.
  - **`Stage`**: Top-level native OS window container with title bar, minimize/maximize/close buttons, and borders.
  - **`Scene`**: The visual container holding the scene graph. A `Stage` can host only one `Scene` at a time, but scenes can be dynamically swapped.
  - **`Node`**: Every visual element in the scene graph (leaf components like `Button`, `Label`, `TextField`, or branch layout containers like `VBox`, `HBox`, `BorderPane`).

---

### Learning Outcome 2: Design UI Layouts (Programmatic & FXML)
- **Self-Rating:** 5 / 5
- **Layout Panes Comparison Matrix:**

| Layout Pane | Arrangement Strategy | Sizing & Expansion Rules | Typical Enterprise Use Case |
| :--- | :--- | :--- | :--- |
| **`VBox`** | Vertical single-column stack | Elements flow top-to-bottom; spacing and alignment configurable | Sidebars, login forms, vertical tool palettes |
| **`HBox`** | Horizontal single-row strip | Elements flow left-to-right; spacing and vertical alignment configurable | Top application headers, toolbars, card strips |
| **`GridPane`** | 2D row/column coordinate grid | Explicit cell placement `(col, row)`; customizable `hgap` and `vgap` | Form fields with labels, calculator keypads, tabular cards |
| **`BorderPane`** | 5 compass regions (`Top`, `Bottom`, `Left`, `Right`, `Center`) | Center region automatically claims all remaining window space | Master application shell, IDE layouts, dashboards |
| **`StackPane`** | Back-to-front layered overlay | Nodes stacked on top of each other | Badges, card backgrounds, modal overlays |

---

### Learning Outcome 3: JavaFX Architecture & Threading Model
- **Self-Rating:** 5 / 5
- **Threading Model Deep Dive:**
  - **`JavaFX Application Thread`**: The single dedicated thread responsible for processing UI input events, calculating layout passes, scene graph mutations, and rendering.
  - **Thread-Safety Invariant**: Modifying any live Scene Graph node from a non-UI background worker thread causes an `IllegalStateException: Not on FX application thread`.
  - **Swing EDT vs JavaFX Thread Comparison**:

| Metric / Dimension | Swing GUI Toolkit | JavaFX Modern Toolkit |
| :--- | :--- | :--- |
| **UI Event Thread** | `Event Dispatch Thread (EDT)` | `JavaFX Application Thread` |
| **Off-Thread Dispatch Helper** | `SwingUtilities.invokeLater(Runnable)` | `Platform.runLater(Runnable)` |
| **Background Task Helper** | `javax.swing.SwingWorker<T, V>` | `javafx.concurrent.Task<V>` / `Service<V>` |
| **Visual Architecture** | Heavyweight / Lightweight component tree | Scene Graph (`Stage` → `Scene` → `Parent` → `Node`) |
| **Rendering Pipeline** | Software 2D Graphics (`Graphics2D`) | Hardware-accelerated GPU pipeline (Prism / Direct3D / Metal / OpenGL) |
| **Styling Mechanism** | LookAndFeel (`UIManager`) / Hardcoded fonts & colors | CSS Stylesheets (`-fx-*` syntax) & programmatic `setStyle()` |
| **UI Declaration** | Java code or GUI form wrappers (`.form`) | Declarative XML (`FXML`) separated from Java controllers |

---

### Learning Outcome 4: Build Functional Applications with MVC Pattern
- **Self-Rating:** 5 / 5
- **Model-View-Controller (MVC) Separation of Concerns:**
  - **View (`login.fxml`)**: Pure declarative XML defining the widget hierarchy, spacing, alignment, and CSS styling. Contains zero business logic or Java code.
  - **Controller (`LoginController.java`)**: Manages user actions and input validation. Injects UI references using `@FXML` and responds to events via `#handleLoginButtonAction`.
  - **Bridge (`FXMLLoader`)**: Parses the FXML file at runtime, constructs the scene graph nodes, instantiates the controller, and injects marked fields.
  - **Capstone Dashboard (`DashboardApp.java`)**: Implements modern SaaS dashboard aesthetics with modular `createCard` factory methods, clean typographic hierarchy (`Font.font("Arial", 28)`), and color-coded KPIs.

---

## Self-Check Questions & Answers

### Q1: What is the difference between a Stage and a Scene?
> **Answer:**  
> A `Stage` represents the physical window managed by the operating system (complete with title bar, minimize/maximize/close icons, and frame borders). A `Scene` is the content container that holds the scene graph nodes displayed inside that window. A `Stage` can host only one `Scene` at a time, but different scenes can be swapped onto the stage dynamically (e.g. transitioning from a Login Scene to a Dashboard Scene).

### Q2: What is the name of the JavaFX main UI thread?
> **Answer:**  
> The **`JavaFX Application Thread`**. Just like the Event Dispatch Thread (EDT) in Swing, all UI component creation, layout calculations, and visual updates must occur on this thread. Long-running or blocking background operations (e.g. database queries, network requests) must be executed on worker threads, with UI updates posted back via `Platform.runLater(Runnable)`.

---

## Verification & Run Commands

All activities compile cleanly and can be launched via Maven, Maven Wrapper, or direct Java commands:

```powershell
# 1. Master GUI Dashboard (Runs all Week 6 activities from one launcher)
java -cp week06-javafx Week6Launcher

# 2. Run via Maven / Maven Wrapper (NetBeans default)
cd week06-javafx
.\mvnw.cmd javafx:run -Djavafx.mainClass=programmaticdemo.Main          # Activity 1
.\mvnw.cmd javafx:run -Djavafx.mainClass=programmaticdemo.LayoutDemo     # Activity 2
.\mvnw.cmd javafx:run -Djavafx.mainClass=programmaticdemo.FXMLApp        # Activity 3 & 4
.\mvnw.cmd javafx:run -Djavafx.mainClass=programmaticdemo.DashboardApp   # Activity 5

# 3. Direct Java Run with JavaFX Module Path (Windows)
java --module-path week06-javafx\lib\win --add-modules javafx.controls,javafx.fxml -cp week06-javafx\target\classes programmaticdemo.Main
java --module-path week06-javafx\lib\win --add-modules javafx.controls,javafx.fxml -cp week06-javafx\target\classes programmaticdemo.LayoutDemo
java --module-path week06-javafx\lib\win --add-modules javafx.controls,javafx.fxml -cp week06-javafx\target\classes programmaticdemo.FXMLApp
java --module-path week06-javafx\lib\win --add-modules javafx.controls,javafx.fxml -cp week06-javafx\target\classes programmaticdemo.DashboardApp
```

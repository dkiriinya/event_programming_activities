# Week 6: Introduction to Modern JavaFX

**Folder:** [`week06-javafx/`](../week06-javafx/)  
**Run GUI Launcher:** `mvn -f week06-javafx/pom.xml javafx:run`  
**Scripts:** `./scripts/run-week6.sh` (Git Bash/Linux/macOS) or `.\scripts\run-week6.ps1` (PowerShell)

---

## Goals

By the end of Week 6, you should be able to:
- [ ] Build a basic JavaFX window using `Application`, `Stage`, and `Scene`.
- [ ] Arrange UI controls with `VBox`, `HBox`, `GridPane`, and `BorderPane`.
- [ ] Design layouts visually using **FXML** (and Scene Builder).
- [ ] Connect FXML views to Java controllers with `@FXML`.

---

## Core Concepts (In Plain English)

Think of JavaFX like a theater play:
- **Stage:** The window itself (has a title, size, and close button).
- **Scene:** What's currently on screen inside the window. You can swap scenes to change screens.
- **Nodes:** Every button, label, text field, or layout container inside the scene.

**Two ways to build a JavaFX UI:**
1. **In Java code (Programmatic):** Create `new Button(...)`, `new VBox(...)`, and wire them in `start(Stage stage)`.
2. **In FXML (Declarative):** Describe the layout in an `.fxml` XML file, and put your click logic in a Controller class using `@FXML`.

---

## Activities

### Activity 1: First JavaFX Window (`Main.java`)
Creates a `Stage`, adds a `Label` and `Button` inside a `VBox`, and displays the `Scene`.
```bash
mvn -f week06-javafx/pom.xml javafx:run -Djavafx.mainClass=programmaticdemo.Main
```

### Activity 2: JavaFX Layouts (`LayoutDemo.java`)
Demonstrates the four core layout containers:
- `VBox` — stacks items vertically
- `HBox` — places items horizontally
- `GridPane` — rows and columns (great for forms)
- `BorderPane` — top, bottom, left, right, and center areas

```bash
mvn -f week06-javafx/pom.xml javafx:run -Djavafx.mainClass=programmaticdemo.LayoutDemo
```

### Activity 3 & 4: FXML + Controller (`login.fxml`, `FXMLApp.java`, `LoginController.java`)
Loads a login screen from `login.fxml` using `FXMLLoader` and connects its fields and button to `LoginController` using `@FXML`.
```bash
mvn -f week06-javafx/pom.xml javafx:run -Djavafx.mainClass=programmaticdemo.FXMLApp
```

### Activity 5: Dashboard App (`DashboardApp.java`)
Combines `BorderPane`, `VBox`, `HBox`, and inline CSS styling to build a modern multi-card dashboard.
```bash
mvn -f week06-javafx/pom.xml javafx:run -Djavafx.mainClass=programmaticdemo.DashboardApp
```

---

## Self-Check

1. **What is the difference between a `Stage` and a `Scene`?**
   `Stage` is the outer OS window. `Scene` holds the UI content inside it.
2. **What does `@FXML` do in a controller class?**
   It links a variable or method in your Java controller to an element with a matching `fx:id` or `onAction` in the `.fxml` file.
3. **Which layout pane is best for a top bar + sidebar + main content area?**
   `BorderPane` (top, left, and center regions).

See [`week06-javafx/SelfAssessment.md`](../week06-javafx/SelfAssessment.md) for the full self-assessment.

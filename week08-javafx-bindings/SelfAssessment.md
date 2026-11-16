# Week 8 — Properties and Bindings in JavaFX: Self-Assessment & Activity Report

---

## Executive Summary & Deliverables Checklist

- [x] **Activity 1: Observable Properties (The Data Model)** — [`src/main/java/com/example/Person.java`](src/main/java/com/example/Person.java)
  - Created an observable JavaFX model using `StringProperty` (`SimpleStringProperty`) for `name` and `IntegerProperty` (`SimpleIntegerProperty`) for `age`.
  - Provided standard JavaBean getters/setters (`getName()`, `setName()`, `getAge()`, `setAge()`) alongside JavaFX property getters (`nameProperty()`, `ageProperty()`).
- [x] **Activity 2: ChangeListener vs. InvalidationListener** — [`src/main/java/com/example/ListenerDemo.java`](src/main/java/com/example/ListenerDemo.java)
  - Attached both an `InvalidationListener` and a `ChangeListener` to `person.nameProperty()`.
  - Verified that transitioning from `""` to `"Alice"` and `"Alice"` to `"Bob"` triggers both listeners, whereas re-setting `"Bob"` a second time triggers neither due to JavaFX's built-in equality check optimization.
- [x] **Activity 3: Unidirectional Binding** — [`src/main/java/com/example/UnidirectionalDemo.java`](src/main/java/com/example/UnidirectionalDemo.java)
  - Bound `greetingLabel.textProperty()` unidirectionally to `nameField.textProperty().concat(", welcome!")`.
  - Verified real-time one-way synchronization as the user types into the `TextField`.
- [x] **Activity 4: Bidirectional Binding** — [`src/main/java/com/example/BidirectionalDemo.java`](src/main/java/com/example/BidirectionalDemo.java)
  - Linked `ageField.textProperty()` (`StringProperty`) and `slider.valueProperty()` (`DoubleProperty`) using `bindBidirectional()` and `NumberStringConverter`.
  - Verified two-way updates: dragging the slider updates the text field, and typing a number updates the slider position.
- [x] **Activity 5: Capstone — Reactive Registration Form** — [`src/main/java/com/example/ReactiveFormApp.java`](src/main/java/com/example/ReactiveFormApp.java)
  - Built a complete reactive form using `GridPane` with live character counting (`bioField.textProperty().length().asString().concat(" / 150 characters")`), live bio preview (`Bindings.concat(...)`), and declarative submit button disable validation (`isEmpty()`, `Bindings.createBooleanBinding(() -> emailField.getText() != null && emailField.getText().contains("@"), emailField.textProperty()).not()`, `length().greaterThan(150)`).
  - *API Compatibility Note:* Since `javafx.beans.property.StringProperty` (`StringExpression`) does not declare a built-in `.contains(String)` method in OpenJFX 21, we wrap `emailField.getText().contains("@")` inside `Bindings.createBooleanBinding(..., emailField.textProperty())` and invert it with `.not()`, preserving the exact reactive semantics from the guide.
- [x] **Master Activity Launcher** — [`src/main/java/com/example/Week8Launcher.java`](src/main/java/com/example/Week8Launcher.java)
  - JavaFX graphical dashboard for one-click launching of any Week 8 activity (including an integrated console output viewer for `ListenerDemo`) or opening all activities concurrently.

---

## Learning Outcomes Self-Assessment

### Learning Outcome 1: Use JavaFX Properties to Create Observable Data Models
- **Self-Rating:** 5 / 5
- [x] I can create a model using `StringProperty` and `IntegerProperty`.
- [x] I understand why properties need standard getters and `property()` getters.
- **Implementation Highlights:**
  - `Person.java` encapsulates state in `final StringProperty name = new SimpleStringProperty("")` and `final IntegerProperty age = new SimpleIntegerProperty(0)`.
  - Exposing `nameProperty()` and `ageProperty()` enables UI controls and listeners to observe or bind directly to the underlying property wrappers.

### Learning Outcome 2: Implement ChangeListeners and InvalidationListeners on Properties
- **Self-Rating:** 5 / 5
- [x] I know the difference between `ChangeListener` and `InvalidationListener`.
- [x] I can add a listener to a property.
- **Observed Console Output in `ListenerDemo`:**
  ```text
  --- Setting name to Alice ---
  1. [InvalidationListener] Name was changed!
  2. [ChangeListener] Name changed from '' to 'Alice'

  --- Setting name to Bob ---
  1. [InvalidationListener] Name was changed!
  2. [ChangeListener] Name changed from 'Alice' to 'Bob'

  --- Setting name to Bob (same value) ---
  ```

### Learning Outcome 3: Create Unidirectional and Bidirectional Bindings Between UI and Model
- **Self-Rating:** 5 / 5
- [x] I can bind a target property to a source property using `.bind()`.
- [x] I can create a bidirectional binding using `.bindBidirectional()`.
- **Implementation Highlights:**
  - Unidirectional binding in `UnidirectionalDemo.java` uses fluent property expressions (`nameField.textProperty().concat(", welcome!")`).
  - Bidirectional binding in `BidirectionalDemo.java` bridges `StringProperty` and `DoubleProperty` cleanly via `javafx.util.converter.NumberStringConverter`.

### Learning Outcome 4: Build a Fully Reactive UI That Updates Automatically with Bindings
- **Self-Rating:** 5 / 5
- [x] I can bind a UI component's visibility or disable state to a conditional expression (e.g., `isEmpty().or(...)`).
- [x] I successfully completed the Reactive Registration Form.
- **Implementation Highlights:**
  - `ReactiveFormApp.java` eliminates imperative `KeyListener` / `ActionListener` boilerplate by expressing form rules declaratively on `submitBtn.disableProperty()`.

---

## Handbook Self-Check Questions & Answers

### Activity 1: Observable Properties (The Data Model)
1. **Q1: Why do we use `StringProperty` instead of a standard `String` in a JavaFX model?**
   - **A:** A standard `String` is immutable and cannot notify any observer when a variable's reference changes. A `StringProperty` wraps the string value in an observable container that allows UI components to attach listeners or bind to it for automatic UI updates.
2. **Q2: What is the purpose of the `nameProperty()` method?**
   - **A:** It returns the `StringProperty` object itself (rather than the plain `String` value), which is required so other components can bind to it or attach listeners (e.g., `label.textProperty().bind(person.nameProperty())`).

### Activity 2: ChangeListener vs. InvalidationListener
1. **Q1: What is the main difference between an `InvalidationListener` and a `ChangeListener`?**
   - **A:** An `InvalidationListener` only notifies that the property value is no longer valid (something changed) without computing the new value eagerly, whereas a `ChangeListener` evaluates the property and supplies both the `oldValue` and the `newValue`.
2. **Q2: If you set a property to the exact same value it already holds, do the listeners fire?**
   - **A:** No. JavaFX checks whether the new value differs from the current value; if they are equal, no invalidation or change event is fired.

### Activity 3: Unidirectional Binding
1. **Q1: What does "unidirectional" mean in the context of bindings?**
   - **A:** Data flows in one direction only: changes in the source property (`nameField.textProperty()`) automatically propagate to the target property (`greetingLabel.textProperty()`), but not vice versa.
2. **Q2: What happens if you try to call `greetingLabel.setText("New Text")` on a bound label?**
   - **A:** JavaFX throws a `java.lang.RuntimeException: A bound value cannot be set.` You must call `.unbind()` on the property before setting its value manually.

### Activity 4: Bidirectional Binding
1. **Q1: What is the main difference between `bind()` and `bindBidirectional()`?**
   - **A:** `bind()` is one-way (source updates target, and target cannot be set manually), whereas `bindBidirectional()` synchronizes both properties in both directions so updating either property updates the other.
2. **Q2: Why did we need `NumberStringConverter` in this activity?**
   - **A:** Because the `Slider` uses a `DoubleProperty` (`valueProperty()`) while the `TextField` uses a `StringProperty` (`textProperty()`). `NumberStringConverter` converts numbers to strings and parses strings back into numbers automatically.

### Activity 5: Capstone — Reactive Registration Form
1. **Q1: Why is it better to bind the `disableProperty()` instead of using an `if` statement inside a `KeyListener`?**
   - **A:** Declarative bindings automatically stay synchronized with all input fields regardless of how the text changes (typing, pasting, programmatic reset), keeping code concise, readable, and free from state-desynchronization bugs.
2. **Q2: What does the `.not()` method do in the email validation binding?**
   - **A:** It negates the `BooleanBinding` returned by `emailField.textProperty().contains("@")`, so the expression evaluates to `true` (disabling the submit button) whenever the email field does **not** contain an `@` symbol.

---

## How to Run & Verify

### Option 1: Modern Maven Runner (Recommended)
```bash
cd week08-javafx-bindings

# Launch Master GUI Dashboard:
mvn javafx:run

# Or run specific activities:
mvn javafx:run -Djavafx.mainClass=com.example.ListenerDemo
mvn javafx:run -Djavafx.mainClass=com.example.UnidirectionalDemo
mvn javafx:run -Djavafx.mainClass=com.example.BidirectionalDemo
mvn javafx:run -Djavafx.mainClass=com.example.ReactiveFormApp
```

### Option 2: PowerShell Runner Scripts
```powershell
.\scripts\run-week8.ps1             # Interactive menu
.\scripts\run-week8.ps1 -Launcher   # Open JavaFX Master Dashboard
.\scripts\run-week8.ps1 -All        # Launch all 4 activities concurrently
.\scripts\run-week8.ps1 -Activity 4 # Run specific activity (1 to 4)
```

### Option 3: Git Bash / Linux / macOS Runner Scripts
```bash
./scripts/run-week8.sh              # Interactive menu
./scripts/run-week8.sh --launcher   # Open JavaFX Master Dashboard
./scripts/run-week8.sh --all        # Launch all 4 activities concurrently
```

### Option 4: AI Agent CLI (`AgentCLI`)
```bash
java -cp scripts AgentCLI list --week 8 --json    # View activity metadata schema
java -cp scripts AgentCLI verify --week 8         # Verify bytecode compilation & patterns
java -cp scripts AgentCLI grade --week 8 --json   # Automated grading rubric
```

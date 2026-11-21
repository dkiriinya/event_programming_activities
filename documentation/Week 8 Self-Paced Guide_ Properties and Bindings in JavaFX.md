# Week 8: Properties and Bindings in JavaFX

**Folder:** [`week08-javafx-bindings/`](../week08-javafx-bindings/)  
**Run GUI Launcher:** `mvn -f week08-javafx-bindings/pom.xml javafx:run`  
**Scripts:** `./scripts/run-week8.sh` (Git Bash/Linux/macOS) or `.\scripts\run-week8.ps1` (PowerShell)

---

## Goals

By the end of Week 8, you should be able to:
- [ ] Build observable data models using JavaFX Properties (`StringProperty`, `IntegerProperty`).
- [ ] React to changes with `ChangeListener` and `InvalidationListener`.
- [ ] Connect UI controls and data models using **unidirectional** (`bind`) and **bidirectional** (`bindBidirectional`) bindings.
- [ ] Build a reactive form where buttons enable/disable automatically based on input.

---

## Core Concepts (In Plain English)

Normal Java fields (`private String name;`) don't tell anyone when they change. JavaFX **Properties** (`SimpleStringProperty`) notify listeners and bound UI controls automatically whenever their value updates.

- **Unidirectional (`A.bind(B)`):** `A` always mirrors `B` (one-way). While bound, you can't set `A` manually.
- **Bidirectional (`A.bindBidirectional(B)`):** `A` and `B` stay in sync both ways (e.g. a `TextField` and a `Person` model).
- **Bindings API (`Bindings.createBooleanBinding`):** Lets you write rules like *"Disable the Submit button whenever Name or Email is empty."*

---

## Activities

### Activity 1: Observable Data Model & Listeners (`Person.java`, `ListenerDemo.java`)
`Person` wraps `name` and `age` in `StringProperty` / `IntegerProperty`. `ListenerDemo` attaches a `ChangeListener` (gives old & new values) and an `InvalidationListener` (fires when value becomes dirty).
```bash
mvn -f week08-javafx-bindings/pom.xml javafx:run -Djavafx.mainClass=com.example.ListenerDemo
```

### Activity 2: Unidirectional Binding (`UnidirectionalDemo.java`)
Binds a `Label`'s text and a `ProgressBar` directly to a `Slider`'s value so moving the slider updates everything automatically with zero event handlers.
```bash
mvn -f week08-javafx-bindings/pom.xml javafx:run -Djavafx.mainClass=com.example.UnidirectionalDemo
```

### Activity 3: Bidirectional Binding (`BidirectionalDemo.java`)
Syncs two controls (and a model property) in both directions — editing either one updates the other immediately.
```bash
mvn -f week08-javafx-bindings/pom.xml javafx:run -Djavafx.mainClass=com.example.BidirectionalDemo
```

### Activity 4: Reactive Form (`ReactiveFormApp.java`)
A live registration form using bindings to:
- Show a live greeting as you type
- Calculate password strength
- Automatically enable/disable the Submit button based on validation rules

```bash
mvn -f week08-javafx-bindings/pom.xml javafx:run -Djavafx.mainClass=com.example.ReactiveFormApp
```

---

## Self-Check

1. **What happens if you call `label.setText("Hi")` after `label.textProperty().bind(...)`?**
   JavaFX throws a `RuntimeException` ("A bound value cannot be set"). Use `bindBidirectional` if both sides need to be editable.
2. **What's the difference between `ChangeListener` and `InvalidationListener`?**
   `ChangeListener` gives you `(observable, oldValue, newValue)` immediately. `InvalidationListener` just tells you the value changed and calculates lazily.
3. **Why use bindings instead of `setOnKeyReleased` for form validation?**
   Bindings are cleaner, less error-prone, and automatically handle paste, clear, and programmatic changes.

See [`week08-javafx-bindings/SelfAssessment.md`](../week08-javafx-bindings/SelfAssessment.md) for the full self-assessment.

# Activity 3: Refactoring to the Observer Pattern - Notes

## Step 2: The Coupling Problem in `TightlyCoupledSystem`

### 1. What happens if we want to add a third display class? Do we have to modify `DataModel`?
Yes. In the tightly coupled system, `DataModel` directly instantiates and calls methods on concrete display classes (e.g., `new LabelDisplay().showValue(value)`). To add a third display (such as `TextLogger`), we would have to edit `DataModel.setValue()` to instantiate and invoke the new display class. This violates the **Open-Closed Principle** (classes should be open for extension but closed for modification).

### 2. What happens if `LabelDisplay` is deleted or renamed?
Because `DataModel` holds direct references to `LabelDisplay` and calls its specific method `showValue()`, deleting or renaming `LabelDisplay` breaks `DataModel` and causes compilation errors. The core data model is fragile and dependent on UI presentation details.

### 3. Can we easily test `DataModel` in isolation?
No. Since `DataModel` hardcodes direct calls to UI/display classes inside `setValue()`, you cannot test the business logic of `DataModel` without also triggering and instantiating those concrete display objects. You cannot mock, stub, or run headless tests easily.

---

## Step 4: Comparison of Approaches

| Aspect | Tightly Coupled System | Observer Pattern System |
| :--- | :--- | :--- |
| **Adding a new display** | Must modify `DataModel` source code | Just create an observer implementing `DataObserver` and register it via `model.addObserver(...)` |
| **Testing `DataModel` in isolation** | Hard — tightly coupled to concrete display classes | Easy — can test without displays, or pass a mock `DataObserver` |
| **Removing a display** | Must modify `DataModel` source code to delete references | Just don't register the observer (or call `removeObserver(...)`) |
| **Dependency Direction** | High-level data model depends on low-level UI details | High-level model depends only on the `DataObserver` interface abstraction (Dependency Inversion) |
| **Extensibility (OCP)** | Closed to extension without modification | Open for extension, closed for modification |

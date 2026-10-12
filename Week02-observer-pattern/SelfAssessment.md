# Week 2 — Observer Pattern Self-Assessment

---

## Learning Outcome 1: Design and Implement the Observer Pattern

### Question 1: Draw the UML diagram of the Observer pattern

```
┌─────────────────────────┐          ┌─────────────────────────┐
│      «interface»        │          │      «interface»        │
│        Subject          │          │        Observer         │
├─────────────────────────┤          ├─────────────────────────┤
│                         │          │                         │
│ + registerObserver(o)   │◆────────▷│ + update(data)          │
│ + removeObserver(o)     │  1    *  │                         │
│ + notifyObservers()     │          └───────────▲─────────────┘
│                         │                      │
└───────────▲─────────────┘                      │ implements
            │                                    │
            │ implements              ┌──────────┴────────────┐
            │                         │   ConcreteObserver     │
┌───────────┴─────────────┐          │  (e.g. LabelDisplay)   │
│    ConcreteSubject       │          ├─────────────────────────┤
│  (e.g. WeatherData)     │          │ - displayState          │
├─────────────────────────┤          │                         │
│ - observers: List       │          │ + update(data)          │
│ - state                 │          └─────────────────────────┘
│                         │
│ + registerObserver(o)   │
│ + removeObserver(o)     │
│ + notifyObservers()     │
│ + setState(data)        │
└─────────────────────────┘
```

**Relationships:**
- `Subject` holds a one-to-many (1..*) aggregation to `Observer`.
- `ConcreteSubject` implements `Subject`.
- `ConcreteObserver` implements `Observer`.
- When `ConcreteSubject.setState()` is called, it invokes `notifyObservers()`, which loops through the observer list and calls `update()` on each `ConcreteObserver`.

---

### Question 2: Write the Java code (from memory) for a Subject interface

```java
public interface Subject {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}
```

These three methods form the minimum contract: **register** adds an observer to the notification list, **remove** takes one off, and **notifyObservers** iterates through the list calling each observer's `update()` method.

---

### Question 3: Why does the Subject need a list of observers?

The Subject maintains a list (e.g., `ArrayList<Observer>`) because the Observer pattern is a **one-to-many** relationship — one Subject can have many Observers. The list serves three purposes:

1. **Registration tracking:** It stores references to every observer that has expressed interest in state changes.
2. **Broadcast notification:** When `notifyObservers()` is called, the Subject iterates over the list and calls `update()` on each observer, ensuring all interested parties receive the new data.
3. **Dynamic management:** Observers can be added or removed at runtime. Without a collection to manage them, the Subject would need hardcoded references to specific observer classes — which is exactly the tight coupling the pattern eliminates.

---

## Learning Outcome 2: Evaluate Legacy java.util.Observer

### Question 4: List three problems with java.util.Observable

1. **Single inheritance constraint:** `Observable` is a **class**, not an interface. Since Java only permits single class inheritance, any class that extends `Observable` cannot extend any other class (e.g., `JPanel`, `Thread`, or a domain superclass). This severely limits design flexibility.

2. **`setChanged()` is confusing and error-prone:** Before calling `notifyObservers()`, you must first call `setChanged()` to flip an internal flag. If you forget, `notifyObservers()` silently does nothing — no error, no warning, no notification. This is one of the most common silent bugs when using the legacy API.

3. **Unspecified notification order:** The order in which observers are notified is not guaranteed or controllable. The internal `Vector` may notify in reverse insertion order, but this is an implementation detail, not a contract. You cannot prioritize certain observers over others.

---

### Question 5: Why was this API deprecated?

Java deprecated `java.util.Observer` and `java.util.Observable` in **Java 9** because of accumulated design flaws that made the API unsuitable for modern development:

- `Observable` being a class (not an interface) breaks composition and prevents extending other classes.
- The `update(Observable, Object)` method signature lacks type safety — the second argument is a raw `Object` requiring manual casting.
- The `setChanged()` / `notifyObservers()` two-step dance is confusing and error-prone.
- The implementation is not truly thread-safe for modern concurrent applications.
- The API is not extensible and cannot be customized or adapted.
- Modern alternatives (custom interfaces, `PropertyChangeListener`, JavaFX bindings, Reactive Streams / `java.util.concurrent.Flow`) are superior in every regard.

---

### Question 6: What is the "pull model" and why is it harder to use?

In the **pull model**, the Subject notifies observers that a change has occurred but does **not** send the updated data directly. Instead, the observer receives only a reference to the Subject (or `null`), and must **pull** the data it needs by calling getter methods on the Subject.

**Example (legacy):**
```java
public void update(Observable o, Object arg) {
    // arg is often null — observer must pull data
    LegacyWeatherData data = (LegacyWeatherData) o;  // downcast required
    float temp = data.getTemperature();               // pull call 1
    float humidity = data.getHumidity();               // pull call 2
}
```

**Why it is harder to use:**
1. **Extra coupling:** The observer must know the concrete type of the Subject and its getter API, creating a dependency on the Subject's implementation details.
2. **Unsafe downcasting:** The observer receives a generic `Observable` reference and must cast it to the correct concrete type, risking `ClassCastException`.
3. **Multiple method calls:** The observer must invoke separate getter methods for each piece of data, instead of receiving everything in a single `update()` call.
4. **Less efficient:** Multiple getter calls instead of one push notification with all data included.

In contrast, the **push model** (used in Activity 1) delivers data directly: `update(float temp, float humidity, float pressure)` — no casting, no getters, no coupling to the concrete Subject class.

---

## Learning Outcome 3: Create a Custom Event Notification System

### Question 7: What methods must a custom Subject interface provide?

A custom Subject interface must provide **three essential methods**:

| Method | Purpose |
|:---|:---|
| `registerObserver(Observer o)` | Adds an observer to the internal notification list so it will receive future updates. |
| `removeObserver(Observer o)` | Removes an observer from the list so it stops receiving notifications. |
| `notifyObservers()` | Iterates through the list of registered observers and calls `update()` on each one, delivering the current state. |

These three methods represent the complete **Subject contract** — the ability to subscribe, unsubscribe, and broadcast.

---

### Question 8: How does the Subject notify all observers?

The Subject notifies all observers by **iterating through its internal list** of registered observers and calling each observer's `update()` method, passing the current state data as arguments:

```java
@Override
public void notifyObservers() {
    for (Observer o : observers) {
        o.update(temperature, humidity, pressure);
    }
}
```

This loop is triggered inside a state-changing method like `setMeasurements()`, which:
1. Updates the Subject's internal state fields.
2. Calls `notifyObservers()`.
3. Each observer's `update()` executes with the new data — the observer decides what to do with it (display, log, compute statistics, etc.).

---

### Question 9: How do you prevent a memory leak when observers are no longer needed?

To prevent memory leaks, you must explicitly **unregister** observers that are no longer needed by calling `removeObserver(o)`:

```java
weatherData.removeObserver(forecastDisplay);
```

**Why this matters:** The Subject holds a strong reference to each registered observer in its list. Even if the observer is no longer used elsewhere in the application, the garbage collector **cannot reclaim it** because the Subject's list still references it. This is a classic memory leak.

**Best practices to prevent leaks:**
1. **Always call `removeObserver()` when an observer is no longer needed** (e.g., when a UI panel is closed or a component is destroyed).
2. **Use `WeakReference<Observer>`** in the observer list so that observers can be garbage-collected when no other strong references exist.
3. **Implement lifecycle hooks** — in frameworks like Swing or JavaFX, remove observers in `dispose()`, `close()`, or cleanup methods.
4. **Clear the entire list** when the Subject itself is being destroyed (`observers.clear()`).

---

## Learning Outcome 4: Compare Different Mechanisms

### Question 10: Name four event notification mechanisms in Java

1. **Custom Observer** — Your own `Observer` and `Subject` interfaces with full control over method signatures, data types, and threading.
2. **`java.util.Observer` / `java.util.Observable`** — The JDK's built-in legacy observer API (deprecated since Java 9).
3. **`PropertyChangeListener` / `PropertyChangeSupport`** — From the `java.beans` package, standard in JavaBeans and Swing; automatically carries property name, old value, and new value.
4. **JavaFX Properties and Bindings** — Modern, reactive data-binding framework from the JavaFX SDK (`javafx.beans.property.*`), supporting `ChangeListener`, `InvalidationListener`, and bidirectional bindings.

---

### Question 11: Which one is best for JavaFX?

**JavaFX Properties and Bindings** is the best mechanism for JavaFX applications. It provides:
- Built-in observable property types (`StringProperty`, `IntegerProperty`, `DoubleProperty`, etc.).
- `ChangeListener` and `InvalidationListener` for reacting to value changes.
- Declarative **bindings** that automatically synchronize UI controls with data models.
- Thread-safe integration with the JavaFX Application Thread.
- Support for bidirectional bindings between properties.

---

### Question 12: Which one is deprecated?

**`java.util.Observer`** (interface) and **`java.util.Observable`** (class) are deprecated since **Java 9**. They should never be used in new code due to the design flaws discussed in Activity 2 (single inheritance, lack of type safety, confusing `setChanged()` requirement, pull model by default, and poor thread safety).

---

## Score Card

| Question | Answered? |
|:---|:---:|
| 1. UML diagram | ✅ |
| 2. Subject interface code | ✅ |
| 3. Why a list of observers? | ✅ |
| 4. Three problems with Observable | ✅ |
| 5. Why deprecated? | ✅ |
| 6. Pull model explanation | ✅ |
| 7. Custom Subject methods | ✅ |
| 8. How Subject notifies observers | ✅ |
| 9. Preventing memory leaks | ✅ |
| 10. Four notification mechanisms | ✅ |
| 11. Best for JavaFX | ✅ |
| 12. Which is deprecated | ✅ |

**Score: 12/12 — Excellent! Mastery of the Observer pattern demonstrated.**

---

---

# One-Page Summary: The Observer Pattern

## What Is It?

The Observer pattern is a **behavioral design pattern** that establishes a one-to-many dependency between objects. A single object called the **Subject** (or "publisher") maintains a list of dependent objects called **Observers** (or "subscribers"). Whenever the Subject's state changes, it automatically notifies all registered Observers by calling their `update()` method — no Observer needs to continuously check or poll for changes.

## How Does It Work?

The pattern has four key participants:

- **Subject (interface):** Defines the contract for registering, removing, and notifying observers.
- **ConcreteSubject (class):** Implements the Subject interface, holds state data and a list of observers. When its state changes (e.g., via `setMeasurements()`), it calls `notifyObservers()`, which iterates through the list and calls `update()` on each observer.
- **Observer (interface):** Defines the `update()` method that every observer must implement — this is the "callback" the Subject invokes.
- **ConcreteObserver (class):** Implements the Observer interface and defines how to react to state changes (e.g., display data, log it, compute statistics).

## Why Use It?

The Observer pattern solves the **tight coupling problem**. Without it, a data model must directly reference every display or consumer class — adding, removing, or changing any consumer forces modifications to the data model. With the Observer pattern, the Subject only knows about the `Observer` interface, not concrete classes. New observers can be added at runtime without touching the Subject's code. This follows the **Open-Closed Principle** (open for extension, closed for modification) and the **Dependency Inversion Principle** (depend on abstractions, not concrete implementations).

## Push vs. Pull

In the **push model**, the Subject sends all updated data directly as arguments to `update()`. In the **pull model**, the Subject merely signals that a change occurred, and the observer must call getter methods to retrieve the data. The push model is simpler, more efficient, and reduces coupling.

## Java's Mechanisms

Java offers four notification mechanisms: **Custom Observer interfaces** (recommended for general use), **`java.util.Observer`/`Observable`** (deprecated — avoid), **`PropertyChangeListener`** (standard for Swing/JavaBeans), and **JavaFX Properties/Bindings** (best for JavaFX apps). For modern Java projects, custom observer interfaces and JavaFX bindings are the recommended approaches.

## Key Takeaway

The Observer pattern decouples the "what changed" (Subject) from the "who cares" (Observers), enabling flexible, maintainable, and extensible event-driven systems.

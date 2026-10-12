# Comparison of Event Notification Mechanisms in Java

## Overview of the Four Mechanisms

| # | Mechanism | Where It Lives | Notes |
|---|---|---|---|
| 1 | **Custom Observer** | Your own interfaces | Full control, lightweight, high cohesion, type-safe (used in Activity 1 & 3) |
| 2 | **`java.util.Observer` / `Observable`** | JDK (`java.util`) | Deprecated since Java 9 due to design flaws, class inheritance constraint, and lack of type safety |
| 3 | **`PropertyChangeListener`** | `java.beans` | Standard in JavaBeans & Swing; uses `PropertyChangeSupport` to manage listeners; carries property names, old, and new values |
| 4 | **JavaFX Properties & Bindings** | JavaFX SDK (`javafx.beans.property.*`) | Modern, rich, reactive UI data-binding framework |

---

## Comparison Table

| Feature | Custom Observer | java.util.Observer | PropertyChangeListener | JavaFX Bindings |
|:---|:---|:---|:---|:---|
| **Push or Pull?** | Both possible (developer's choice) | Pull by default (`null` arg) | Push (carries property name, old + new value) | Push (observable values and listeners) |
| **Is it deprecated?** | No | Yes (Java 9+) | No | No |
| **Requires inheritance?** | No (interface-based) | Yes (`Observable` is a concrete class) | No (can use composition with `PropertyChangeSupport`) | No (properties held as composition fields) |
| **Thread-safe?** | Depends on implementation | No (synchronized partially, but not robust) | Yes (`PropertyChangeSupport` uses copy-on-write array) | Yes (UI thread-bound in JavaFX) |
| **Auto-generates boilerplate?** | No (manual collection & loop) | No | Yes (via `PropertyChangeSupport` helper) | Yes (via built-in property classes and bindings) |
| **Best for...** | Custom domain systems, microservices, game engines | Legacy code maintenance | JavaBeans / Swing desktop UI | JavaFX rich client applications |
| **Can have multiple listeners?** | Yes | Yes | Yes | Yes |
| **Can be typed to send any data?** | Yes (strongly-typed parameters/generics) | No (accepts generic `Object`) | Semi-typed (values wrapped in `Object`, or typed subclasses) | Yes (generic property types: `StringProperty`, `IntegerProperty`, etc.) |
| **Recommended today?** | Yes | No | Only for legacy / Swing | Yes (for JavaFX) |

---

## Which One Should You Use?

- **Plain Java project with custom domain logic:**  
  → **Custom Observer**: Gives you complete control over method signatures, data types, threading behavior, and avoids unnecessary framework dependencies.
- **JavaFX application:**  
  → **JavaFX Bindings / Properties**: Provides out-of-the-box reactive two-way bindings, change listeners, and invalidation listeners tightly integrated into UI controls.
- **Desktop application using Swing or JavaBeans:**  
  → **`PropertyChangeListener`**: Standard for Swing components and legacy JavaBeans architectures; carries change metadata (old and new values).
- **Modern production applications:**  
  → **Never use `java.util.Observer` / `java.util.Observable`**: Deprecated in Java 9, inflexible due to single class inheritance, lacks type safety, and prone to bugs when `setChanged()` is omitted.

---

## Step 4: Self-Check Answers

### 1. Which mechanism carries both the old and new value of a property?
**Answer:** `PropertyChangeListener` (via `PropertyChangeEvent`, using `getOldValue()` and `getNewValue()`).

### 2. Which mechanism requires a helper class to manage the listener list?
**Answer:** `PropertyChangeListener` typically uses the helper class `java.beans.PropertyChangeSupport` to register listeners and fire property change events automatically.

### 3. Which two mechanisms are recommended for modern Java?
**Answer:** **Custom Observer interfaces** (for general Java backend, domain models, and custom architectures) and **JavaFX Properties/Bindings** (for modern reactive desktop client applications).

### 4. Which mechanism is deprecated and should be avoided?
**Answer:** `java.util.Observer` and `java.util.Observable` (deprecated since Java 9).

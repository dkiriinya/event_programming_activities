# Week 2: The Observer Pattern — Self-Paced Practical Guide

Comprehensive hands-on student guide for understanding, implementing, refactoring, and comparing the Observer Design Pattern in Java.

---

## How to Use This Guide

Work through each activity in order. Each activity builds on the previous one. Complete all activities hands-on by writing, compiling, and running real Java code in your project workspace.

- **Estimated Total Time:** 3 hours (can be split across multiple sessions)
- **Primary Language & Framework:** Java (Standard JDK 11+, recommended JDK 17/21/27)
- **Target Folder:** `Week02-observer-pattern/`

---

## What You Will Achieve By The End

1. **Design and implement** the classic GoF Observer pattern from scratch in Java.
2. **Evaluate** the advantages, pitfalls, and deprecation reasons of the legacy `java.util.Observer` / `Observable` classes.
3. **Refactor** a tightly-coupled system into a clean, decoupled custom event notification system with multiple observers.
4. **Compare** different event notification mechanisms in modern Java (`Custom Observer`, `PropertyChangeListener`, and `JavaFX Properties`).

### Progress Checklist

- [ ] **Activity 1:** Build a Weather Station Using the Classic Observer Pattern
- [ ] **Activity 2:** Evaluate Legacy `java.util.Observer` and Document Limitations
- [ ] **Activity 3:** Refactor Tightly-Coupled System into a Decoupled Observer Pattern
- [ ] **Activity 4:** Compare Event Notification Mechanisms in Java (`PropertyChangeListener`)
- [ ] **Activity 5:** Final Knowledge Self-Assessment

---

## Directory Structure Setup

All code for this guide belongs in `Week02-observer-pattern/`:

```text
event-driven-programming-java/
└── Week02-observer-pattern/
    ├── activity1-weather-station/
    │   ├── Observer.java
    │   ├── Subject.java
    │   ├── WeatherData.java
    │   ├── CurrentConditionsDisplay.java
    │   ├── StatisticsDisplay.java
    │   ├── ForecastDisplay.java
    │   └── WeatherStationMain.java
    ├── activity2-legacy-observer/
    │   ├── LegacyWeatherData.java
    │   ├── LegacyDisplay.java
    │   ├── LegacyMain.java
    │   └── LegacyLimitations.md
    ├── activity3-custom-observer/
    │   ├── TightlyCoupledSystem.java
    │   ├── DataObserver.java
    │   ├── DataSubject.java
    │   ├── DataModel.java
    │   ├── LabelDisplay.java
    │   ├── BarChartDisplay.java
    │   ├── TextLogger.java
    │   └── RefactoredMain.java
    ├── activity4-comparison/
    │   ├── PropertyChangeDemo.java
    │   └── Comparison.md
    └── SelfAssessment.md
```

---

## Activity 1: Build a Weather Station Using the Observer Pattern

- **Duration:** 90 minutes
- **Learning Outcomes Addressed:** #1, #3
- **Purpose:** Build a complete Observer pattern system from scratch. Create a `WeatherData` publisher (`Subject`) that notifies multiple display modules (`Observers`) when temperature, humidity, and atmospheric pressure change.

### Step 1: Understand the Pattern Structure

The classic Gang of Four (GoF) Observer pattern defines a one-to-many dependency between objects:
- **`Subject` (Publisher):** Maintains an internal collection of registered observers. Provides `registerObserver()`, `removeObserver()`, and `notifyObservers()`.
- **`Observer` (Subscriber):** Defines an `update()` callback contract.
- **`ConcreteSubject` (`WeatherData`):** Stores weather state and broadcasts changes to all observers.
- **`ConcreteObserver` (`Displays`):** Responds to state changes without the Subject knowing concrete display implementations.

### Step 2: Create the `Observer` Interface

Create file: `Week02-observer-pattern/activity1-weather-station/Observer.java`:

```java
// ============================================================
// OBSERVER INTERFACE
// ============================================================
// Any class that wants to be notified of changes in the
// Subject must implement this interface.
public interface Observer {
    // Called by the Subject whenever its state changes (Push model).
    void update(float temperature, float humidity, float pressure);
}
```

### Step 3: Create the `Subject` Interface

Create file: `Week02-observer-pattern/activity1-weather-station/Subject.java`:

```java
// ============================================================
// SUBJECT INTERFACE
// ============================================================
// Defines the operations that any "publisher" must support.
public interface Subject {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}
```

### Step 4: Create the `WeatherData` Concrete Subject

Create file: `Week02-observer-pattern/activity1-weather-station/WeatherData.java`:

```java
import java.util.ArrayList;
import java.util.List;

public class WeatherData implements Subject {
    private final List<Observer> observers;
    private float temperature;
    private float humidity;
    private float pressure;

    public WeatherData() {
        observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
        System.out.println("[WeatherData] Observer registered: " + o.getClass().getSimpleName());
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
        System.out.println("[WeatherData] Observer removed: " + o.getClass().getSimpleName());
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(temperature, humidity, pressure);
        }
    }

    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        System.out.println("\n[WeatherData] New measurements received!");
        notifyObservers();
    }
}
```

### Step 5: Implement the Three Concrete Observer Displays

#### 1. Current Conditions Display
Create file: `Week02-observer-pattern/activity1-weather-station/CurrentConditionsDisplay.java`:

```java
public class CurrentConditionsDisplay implements Observer {
    @Override
    public void update(float temperature, float humidity, float pressure) {
        System.out.println("  [Current] Temp: " + temperature + "°C, Humidity: " + humidity + "%");
    }
}
```

#### 2. Statistics Display
Create file: `Week02-observer-pattern/activity1-weather-station/StatisticsDisplay.java`:

```java
public class StatisticsDisplay implements Observer {
    private float maxTemp = Float.MIN_VALUE;
    private float minTemp = Float.MAX_VALUE;

    @Override
    public void update(float temperature, float humidity, float pressure) {
        maxTemp = Math.max(maxTemp, temperature);
        minTemp = Math.min(minTemp, temperature);
        System.out.println("  [Statistics] Max: " + maxTemp + "°C, Min: " + minTemp + "°C");
    }
}
```

#### 3. Forecast Display
Create file: `Week02-observer-pattern/activity1-weather-station/ForecastDisplay.java`:

```java
public class ForecastDisplay implements Observer {
    private float lastPressure = 1013.0f;

    @Override
    public void update(float temperature, float humidity, float pressure) {
        String forecast;
        if (pressure > lastPressure) {
            forecast = "Improving weather on the way!";
        } else if (pressure < lastPressure) {
            forecast = "Cooler, rainy weather coming.";
        } else {
            forecast = "More of the same.";
        }
        System.out.println("  [Forecast] " + forecast);
        lastPressure = pressure;
    }
}
```

### Step 6: Create the Main Runner

Create file: `Week02-observer-pattern/activity1-weather-station/WeatherStationMain.java`:

```java
public class WeatherStationMain {
    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();

        CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay();
        StatisticsDisplay statsDisplay = new StatisticsDisplay();
        ForecastDisplay forecastDisplay = new ForecastDisplay();

        weatherData.registerObserver(currentDisplay);
        weatherData.registerObserver(statsDisplay);
        weatherData.registerObserver(forecastDisplay);

        weatherData.setMeasurements(25.0f, 65.0f, 1013.0f);
        weatherData.setMeasurements(28.0f, 70.0f, 1012.0f);
        weatherData.setMeasurements(22.0f, 60.0f, 1015.0f);

        System.out.println("\n--- Removing ForecastDisplay ---");
        weatherData.removeObserver(forecastDisplay);
        weatherData.setMeasurements(20.0f, 55.0f, 1018.0f);
    }
}
```

---

## Activity 2: Evaluate Legacy `java.util.Observer`

- **Duration:** 45 minutes
- **Learning Outcomes Addressed:** #2
- **Purpose:** Study Java's historical `java.util.Observer` and `java.util.Observable` classes (deprecated in Java 9), and analyze why custom interfaces are superior.

### Key Deprecation Flaws of `java.util.Observable`
1. **Class, Not an Interface:** Because `Observable` is a class, a subject cannot extend any other superclass (single-inheritance limitation).
2. **Hidden State Flag (`setChanged()`):** You must remember to call `setChanged()` prior to `notifyObservers()`, or notifications silently fail.
3. **Pull Model Inefficiency:** Observers are passed generic `Object` references and must cast and query the Observable for data.
4. **Unspecified Notification Order:** Observers may be notified in unpredictable order.
5. **Lack of Thread Safety:** Does not serialize concurrent subscriber modifications safely.

---

## Activity 3: Refactor a Tightly-Coupled System into Observer Pattern

- **Duration:** 60 minutes
- **Learning Outcomes Addressed:** #1, #3
- **Purpose:** Take a tightly-coupled data model (which directly calls hardcoded display classes) and refactor it into clean, loosely-coupled interfaces.

### Comparison: Tight vs. Loose Coupling

| Aspect | Tightly Coupled (`TightlyCoupledSystem.java`) | Decoupled Observer (`RefactoredMain.java`) |
| :--- | :--- | :--- |
| **Adding a New Display** | Must edit and recompile `DataModel` | Just register a new observer via `model.addObserver()` |
| **Testing in Isolation** | Impossible without mocking concrete displays | Simple mock `DataObserver` implementation |
| **Removing a Display** | Must delete code inside `DataModel` | Simply call `model.removeObserver()` |
| **Single Responsibility** | Model manages data AND coordinates UI | Model only manages state and dispatches events |

---

## Activity 4: Compare Event Notification Mechanisms in Java

- **Duration:** 60 minutes
- **Learning Outcomes Addressed:** #4
- **Purpose:** Compare custom observer interfaces against `PropertyChangeListener` from `java.beans`.

### Comprehensive Comparison Matrix

| Feature | Custom Observer Pattern | Legacy `java.util.Observer` | `PropertyChangeListener` | Modern JavaFX Bindings |
| :--- | :--- | :--- | :--- | :--- |
| **Push or Pull?** | Both possible | Pull by default | Push (carries old + new value) | Push |
| **Deprecated?** | No | Yes (Java 9+) | No | No |
| **Requires Inheritance?** | No (Interface) | Yes (`extends Observable`) | No (Interface) | No |
| **Thread-Safe Support?** | Under developer control | No | Yes (via `PropertyChangeSupport`) | Yes |
| **Auto Boilerplate Management?** | Manual List | Internal Vector | Yes (`PropertyChangeSupport`) | Built-in |
| **Best Used For** | Core domain logic, custom events | Legacy maintenance | JavaBeans, Swing data binding | Desktop JavaFX applications |

---

## Universal Execution Instructions

You can run Week 2 activities using the interactive scripts or directly via Java:

### 1. Master GUI Dashboard (Zero-Dependency)
```bash
java -cp Week02-observer-pattern Week2Launcher
```

### 2. Git Bash (`.sh`)
```bash
./scripts/run-week2.sh           # Interactive menu
./scripts/run-week2.sh --all     # Run all observer simulations sequentially
./scripts/run-week2.sh --launcher # Open Master GUI Dashboard
```

### 3. Windows PowerShell (`.ps1` or Batch)
```powershell
.\scripts\run-week2.ps1          # Interactive menu
.\scripts\run-week2.ps1 -All     # Run all observer simulations sequentially
.\scripts\run-week2.ps1 -Launcher # Open Master GUI Dashboard
```

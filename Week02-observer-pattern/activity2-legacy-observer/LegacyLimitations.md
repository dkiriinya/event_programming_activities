# Limitations of java.util.Observer and java.util.Observable

## Limitation 1: Single Inheritance
Explain: Why is it a problem that Observable is a CLASS, not an INTERFACE?

Your answer:
In Java, a class can only extend a single superclass (single inheritance). Because `java.util.Observable` is a concrete class rather than an interface, any class that wants to act as an `Observable` subject must inherit directly from it. If your class already needs to extend another class (for instance, a GUI class like `JPanel` or `JFrame`, or a domain class like `Device` or `Sensor`), it cannot also extend `Observable`. This severely violates object-oriented design flexibility and the principle to "favor composition/interfaces over inheritance."

---

## Limitation 2: Cannot Change the Notification Order
Explain: In what order are observers notified? Can you control this?

Your answer:
`java.util.Observable` maintains its observers internally using a `Vector`. Historically, notification occurred in the reverse order of registration (LIFO) or in an unspecified order. There is no built-in mechanism or API hook to customize, control, or guarantee the sequence in which observers receive notifications (such as FIFO, priority-based ordering, or parallel notifications).

---

## Limitation 3: setChanged() is Confusing
Explain: Why must you call setChanged() before notifyObservers()?

Your answer:
`Observable` includes an internal boolean flag `changed`. Calling `notifyObservers()` checks whether `hasChanged()` is `true`. If `setChanged()` was not called before `notifyObservers()`, the notification is completely and silently ignored—none of the observers will be notified. Furthermore, `setChanged()` is protected, meaning it can only be invoked from within subclasses of `Observable`, making it impossible to trigger notifications from external coordinators or composition wrappers without exposing custom public methods. Forgetting `setChanged()` is one of the most common silent bugs when using legacy observer classes.

---

## Limitation 4: Pull Model by Default
Explain: How does the observer get the data? Is this efficient?

Your answer:
By default, calling `notifyObservers()` without arguments passes `null` as the second argument (`Object arg`) to `Observer.update(Observable o, Object arg)`. The observer only receives a reference to the `Observable` instance (`o`). To get updated values, the observer must perform a type check/downcast (`(LegacyWeatherData) o`) and invoke multiple getter methods (`getTemperature()`, `getHumidity()`). This is less efficient, increases coupling between the observer and the concrete subject implementation, and requires extra getter calls compared to a push model where the updated data is passed directly in the notification call.

---

## Limitation 5: Deprecated Since Java 9
Explain: Why did Java deprecate these classes?

Your answer:
Java officially deprecated `java.util.Observer` and `java.util.Observable` in Java 9 (JEP / deprecation notice) for several key design flaws:
1. **Class instead of Interface**: `Observable` is an un-composable class rather than an interface.
2. **Lack of Rich Event Support and Type Safety**: Arguments are passed as raw `Object`, requiring type casting, and the event model does not convey what specifically changed.
3. **Thread Safety Issues**: While methods like `addObserver()` are synchronized, the ordering and state change semantics are not robust for modern concurrent applications.
4. **Poor Extensibility and Control**: Fixed notification mechanisms, silent failures when `setChanged()` is omitted, and tight coupling. Modern Java applications use custom listener/observer interfaces, `PropertyChangeListener` / `PropertyChangeSupport`, or reactive streams (`java.util.concurrent.Flow`).

---

## Step 4: Self-Check Answers

### 1. Why can't you extend another class if you already extend Observable?
Java enforces single class inheritance. A Java class can only extend one superclass using the `extends` keyword. Since `Observable` is a class, extending it consumes the class's single inheritance hierarchy, preventing the class from extending any other domain or framework class.

### 2. What is the difference between the push model and the pull model?
- **Push Model**: The Subject sends the updated data directly to the observer as arguments to the `update()` method (e.g., `update(temperature, humidity, pressure)`). The observer receives the data immediately without needing to know or call getters on the subject.
- **Pull Model**: The Subject notifies the observer that a change occurred (often passing only a reference to itself, `update(this, null)`). The observer is responsible for calling getter methods on the Subject to pull whatever specific data it needs.

### 3. Why did Java deprecate these classes?
Java deprecated `java.util.Observer` and `java.util.Observable` in Java 9 because `Observable` is an inflexible class rather than an interface, lacks generics and type safety (forcing `Object` downcasts), has confusing state requirements (`setChanged()`), provides no ordering guarantees, and fails to provide a scalable, modern event-notification model compared to custom interfaces, Java Beans `PropertyChangeSupport`, or Reactive Streams (`Flow` API).

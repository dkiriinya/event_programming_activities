# Week 10 Final Project Capstone: University Management System

**The Event-Driven Architecture (EDA) Approach with Cloud PostgreSQL on Neon**  
**Author:** Don Gitonga Kiriinya  
**Course:** Event-Driven Programming in Java  
**Project Directory:** `UniversitySystem/`

---

## 🎯 Executive Overview & Learning Outcomes

The **University Management System** is the culminating capstone project demonstrating comprehensive mastery of Event-Driven Architecture (EDA), multithreaded concurrency, observable data models, and modern cloud database persistence.

### Learning Outcomes Mapping

| Outcome | Description | Practical Implementation in Project |
| :--- | :--- | :--- |
| **LO1 (EDA Principles)** | Separation of Concerns: decouple Event Producers (UI controls) from Event Consumers (Database & Services). | The UI never accesses JDBC directly. All mutations and queries are dispatched as asynchronous tasks, preserving responsiveness. |
| **LO2 (Producer-Consumer & Background Tasks)** | Execute non-blocking I/O queries off the JavaFX Application Thread. | [`LoadStudentsTask.java`](file:///z:/home/thema/school-projects/event_programming_activities/UniversitySystem/src/main/java/com/university/LoadStudentsTask.java) extends `javafx.concurrent.Task<ObservableList<Student>>` to load database rows in the background. |
| **LO3 (Scheduled Tasks)** | Execute recurring background monitoring tasks with clean lifecycle management. | [`App.java`](file:///z:/home/thema/school-projects/event_programming_activities/UniversitySystem/src/main/java/com/university/App.java) configures a `ScheduledExecutorService` running a heartbeat check every 5 seconds, terminating gracefully on window close. |
| **LO4 (Event Communication & Bindings)** | Reactive component communication via UI events and data bindings without tight coupling. | [`Student.java`](file:///z:/home/thema/school-projects/event_programming_activities/UniversitySystem/src/main/java/com/university/Student.java) uses JavaFX properties. Form validation binds the **Save** button's `disableProperty` to field emptiness. |

---

## ☁️ Database Engine Choice: PostgreSQL on Neon

While the initial assignment outline referenced local MySQL, this implementation utilizes **PostgreSQL on Neon** for modern cloud persistence:
1. **Serverless Cloud Architecture**: Accessible from any environment without requiring a local database server process running on the grader or instructor's machine.
2. **Encrypted Security by Default**: Requires SSL (`sslmode=require`) and secure credential handling.
3. **Automated Schema Initialization**: [`DatabaseUtil.java`](file:///z:/home/thema/school-projects/event_programming_activities/UniversitySystem/src/main/java/com/university/DatabaseUtil.java) auto-detects missing tables and runs DDL statements (`CREATE TABLE IF NOT EXISTS`) and default seeds automatically on first run.
4. **Environment-Driven Configuration**: Automatically reads credentials from `.env.local`, `.env`, or system environment variables (`DATABASE_URL`, `POSTGRES_URL`).

### Database Relational Schema

```sql
-- 1. Academic Programs (Catalog)
CREATE TABLE IF NOT EXISTS programs (
    program_id SERIAL PRIMARY KEY,
    program_name VARCHAR(100) NOT NULL,
    required_units INT NOT NULL
);

-- 2. Students Directory
CREATE TABLE IF NOT EXISTS students (
    student_id SERIAL PRIMARY KEY,
    registration_number VARCHAR(20) UNIQUE NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    program_id INT REFERENCES programs(program_id)
);
```

Default seeds (`Bachelor of Computer Science`, `Diploma in IT`) are automatically populated if the `programs` table is empty.

---

## 🏗️ Architecture & Component Walkthrough

```
UniversitySystem/
├── pom.xml                                        # Maven configuration (JavaFX 21, pgjdbc 42.7.3)
├── lib/
│   └── postgresql-42.7.3.jar                      # Standalone JDBC driver binary
└── src/
    ├── main/
    │   ├── java/com/university/
    │   │   ├── App.java                           # Application entry & LO3 scheduled heartbeat
    │   │   ├── DatabaseUtil.java                  # Neon SSL connection & schema initializer
    │   │   ├── LoadStudentsTask.java              # LO2 Producer-Consumer background worker
    │   │   ├── MainController.java                # Shell navigation & view switcher
    │   │   ├── Student.java                       # LO4 Observable data model
    │   │   ├── StudentController.java             # LO1/LO4 Event handlers & validation bindings
    │   │   └── UniversitySystemTest.java          # Headless E2E integration test suite
    │   └── resources/com/university/
    │       ├── MainView.fxml                      # Navigation sidebar & dynamic content pane
    │       └── StudentView.fxml                   # Student directory form, validation & TableView
```

### 1. The Observable Model ([`Student.java`](file:///z:/home/thema/school-projects/event_programming_activities/UniversitySystem/src/main/java/com/university/Student.java))
Encapsulates student attributes using JavaFX properties (`IntegerProperty`, `StringProperty`). This enables the `TableView` in `StudentView.fxml` to bind directly to property changes.

```java
public class Student {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty regNumber = new SimpleStringProperty();
    private final StringProperty firstName = new SimpleStringProperty();
    private final StringProperty lastName = new SimpleStringProperty();

    public Student(int id, String regNumber, String firstName, String lastName) {
        this.id.set(id);
        this.regNumber.set(regNumber);
        this.firstName.set(firstName);
        this.lastName.set(lastName);
    }

    public IntegerProperty idProperty() { return id; }
    public StringProperty regNumberProperty() { return regNumber; }
    public StringProperty firstNameProperty() { return firstName; }
    public StringProperty lastNameProperty() { return lastName; }
}
```

### 2. The Asynchronous Query Worker ([`LoadStudentsTask.java`](file:///z:/home/thema/school-projects/event_programming_activities/UniversitySystem/src/main/java/com/university/LoadStudentsTask.java))
Implements the **Producer-Consumer pattern** (LO2). The database reading loop runs on a background worker thread (`call()`), populating an `ObservableList<Student>`. Once complete, the JavaFX runtime delivers the result back to the UI thread via `setOnSucceeded()`.

```java
public class LoadStudentsTask extends Task<ObservableList<Student>> {
    @Override
    protected ObservableList<Student> call() throws Exception {
        ObservableList<Student> students = FXCollections.observableArrayList();
        String sql = "SELECT student_id, registration_number, first_name, last_name FROM students ORDER BY student_id ASC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                students.add(new Student(
                    rs.getInt("student_id"),
                    rs.getString("registration_number"),
                    rs.getString("first_name"),
                    rs.getString("last_name")
                ));
            }
        }
        return students;
    }
}
```

### 3. Reactive UI & Input Validation ([`StudentController.java`](file:///z:/home/thema/school-projects/event_programming_activities/UniversitySystem/src/main/java/com/university/StudentController.java))
- **Data Binding**: Columns are mapped via `setCellValueFactory` to the model properties.
- **Dynamic Validation**: The **Save** button is disabled whenever any required field is blank:
  ```java
  saveButton.disableProperty().bind(
      regField.textProperty().isEmpty()
      .or(firstNameField.textProperty().isEmpty())
      .or(lastNameField.textProperty().isEmpty())
  );
  ```
- **Non-Blocking Insert**: When clicking "Save Student", a background `Task<Void>` executes the SQL `INSERT`, clearing fields and refreshing the table upon completion without freezing the UI.

### 4. Scheduled Background Worker ([`App.java`](file:///z:/home/thema/school-projects/event_programming_activities/UniversitySystem/src/main/java/com/university/App.java))
Demonstrates LO3 with a `ScheduledExecutorService` configured during application startup:
```java
scheduler = Executors.newScheduledThreadPool(1);
Runnable heartbeat = () -> {
    System.out.println("System Heartbeat: Checking for updates at " + new java.util.Date());
};
scheduler.scheduleAtFixedRate(heartbeat, 0, 5, TimeUnit.SECONDS);
```
On application shutdown (`stop()`), the scheduler is terminated cleanly using `shutdownNow()`.

---

## 🚀 Running & Verification Instructions

### 1. Interactive Desktop GUI Application
Launch the complete JavaFX desktop application using any of the following options:

- **Via Maven (Universal):**
  ```bash
  mvn -f UniversitySystem/pom.xml javafx:run
  ```
- **Via NPM:**
  ```bash
  npm run start:university
  ```
- **Via PowerShell:**
  ```powershell
  .\scripts\run-university.ps1
  ```
- **Via Git Bash / Linux (`.sh`):**
  ```bash
  ./scripts/run-university.sh
  ```

### 2. Headless Database Integration Test
To verify the cloud PostgreSQL connection, schema creation, record insertion, and query retrieval without opening a GUI window:

```bash
npm run test:university
```
*Or directly via Java:*
```powershell
java -cp "UniversitySystem/target/classes;UniversitySystem/lib/postgresql-42.7.3.jar" com.university.UniversitySystemTest
```

**Expected Successful Output:**
```text
===============================================================================
 > Neon PostgreSQL End-to-End Service Test
===============================================================================
1. Loading JDBC configuration...
   Target URL: jdbc:postgresql://ep-lucky-grass-bem18bok-pooler.c-9.us-east-2.aws.neon.tech/neondb?channel_binding=require&sslmode=require
2. Establishing SSL connection to Neon...
   Connection successfully established!
3. Initializing schema (programs & students tables)...
Neon PostgreSQL database tables initialized successfully.
4. Inserting test record...
   Inserted test student: TEST-25740 (Don Gitonga), rows affected: 1
5. Querying database records...
   [1] ID: 4 | Reg: TEST-25740 | Name: Don Gitonga | Email: test-25740@university.edu
   Query retrieved active record(s).
-------------------------------------------------------------------------------
✓ Neon PostgreSQL End-to-End Test PASSED in 7.50s!
===============================================================================
```

---

## ❓ Self-Check Questions & Answers

1. **Why should database queries never run on the JavaFX Application Thread?**
   - *Answer:* The JavaFX Application Thread is responsible for processing UI events, rendering frames (at 60 FPS), and handling user interactions. Blocking it with network I/O or database queries freezes the UI and causes the OS to report the application as "Not Responding".

2. **How does `Task<T>` safely communicate results back to the JavaFX UI?**
   - *Answer:* `Task.call()` executes on a background thread from a separate thread pool. The completion callbacks (`setOnSucceeded`, `setOnFailed`) are automatically scheduled to run on the JavaFX Application Thread via `Platform.runLater()`.

3. **How does PostgreSQL JDBC URL syntax differ from standard connection URIs?**
   - *Answer:* Standard URIs allow `postgresql://user:pass@host/db`. The PostgreSQL JDBC driver does not support credentials in the authority position (`//user:pass@host`), parsing anything after the colon as a port number. Credentials must instead be parsed and supplied via `Properties` or query parameters to `DriverManager.getConnection()`.

4. **Why is `ScheduledExecutorService` preferred over `Thread.sleep()` in a loop?**
   - *Answer:* `ScheduledExecutorService` manages a reusable worker thread pool, provides robust periodic scheduling mechanisms (`scheduleAtFixedRate`, `scheduleWithFixedDelay`), and supports clean lifecycle shutdown without blocking application execution.

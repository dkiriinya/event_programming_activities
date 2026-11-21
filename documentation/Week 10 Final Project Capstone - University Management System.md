# Week 10 Capstone: University Management System

**Folder:** [`UniversitySystem/`](../UniversitySystem/)  
**Run App:** `mvn -f UniversitySystem/pom.xml javafx:run` (or `npm run start:university`)  
**Scripts:** `./scripts/run-university.sh` (Git Bash/Linux/macOS) or `.\scripts\run-university.ps1` (PowerShell)

---

## What This Project Does

A JavaFX desktop app for managing university students and programs, backed by a cloud **PostgreSQL (Neon)** database. It brings together everything from Weeks 1–9:
- **Decoupled UI & Database:** The UI never blocks on database calls; all queries run in background threads.
- **Observable Models & Bindings:** `Student` uses JavaFX properties so the table updates automatically, and the Add button stays disabled until required fields are filled.
- **Background Tasks:** [`LoadStudentsTask.java`](../UniversitySystem/src/main/java/com/university/LoadStudentsTask.java) loads students off the UI thread using `javafx.concurrent.Task`.
- **Scheduled Heartbeat:** [`App.java`](../UniversitySystem/src/main/java/com/university/App.java) runs a 5-second background check using `ScheduledExecutorService` and shuts it down cleanly on exit.

---

## Project Structure

```text
UniversitySystem/
├── pom.xml                                        # Maven config (JavaFX 21, PostgreSQL JDBC)
├── lib/postgresql-42.7.3.jar                      # Standalone JDBC driver
└── src/main/
    ├── java/com/university/
    │   ├── App.java                               # App entry point & scheduled heartbeat
    │   ├── DatabaseUtil.java                      # Neon SSL connection & auto table setup
    │   ├── LoadStudentsTask.java                  # Background task to load students
    │   ├── MainController.java                    # Sidebar navigation
    │   ├── Student.java                           # Observable Student model
    │   ├── StudentController.java                 # CRUD actions, live search, validation
    │   ├── SeedStudents.java                      # Sample data seeder
    │   └── UniversitySystemTest.java              # Database integration test
    └── resources/com/university/
        ├── MainView.fxml                          # Main window layout
        └── StudentView.fxml                       # Student form & table layout
```

---

## Database Setup (PostgreSQL on Neon)

1. [`DatabaseUtil.java`](../UniversitySystem/src/main/java/com/university/DatabaseUtil.java) reads `DATABASE_URL` or `POSTGRES_URL` from `.env.local`, `.env`, or your system environment variables.
2. On first run, it automatically creates the `programs` and `students` tables and seeds default programs (`Bachelor of Computer Science`, `Diploma in IT`) if empty.

---

## How Key Parts Work

1. **Observable Model ([`Student.java`](../UniversitySystem/src/main/java/com/university/Student.java)):** Uses `IntegerProperty` and `StringProperty` so `TableView` columns stay in sync.
2. **Background Loading ([`LoadStudentsTask.java`](../UniversitySystem/src/main/java/com/university/LoadStudentsTask.java)):** Extends `Task<ObservableList<Student>>` to query PostgreSQL in `call()` and pass results back to the UI in `setOnSucceeded()`.
3. **CRUD & Live Search ([`StudentController.java`](../UniversitySystem/src/main/java/com/university/StudentController.java)):**
   - **Add / Update / Delete:** Runs SQL statements on background threads and refreshes the table.
   - **Live Search:** Wraps the list in `FilteredList` and `SortedList` to filter as you type.
   - **Validation:** Binds `disableProperty()` on the Add button to required fields.

---

## Testing & Verification

Run the headless database test (no GUI window needed):
```bash
npm run test:university
```
Or directly with Java:
```powershell
java -cp "UniversitySystem/target/classes;UniversitySystem/lib/postgresql-42.7.3.jar" com.university.UniversitySystemTest
```
It connects to Neon over SSL, initializes the tables, inserts a test student, queries it back, and prints `PASSED`.

---

## Self-Check

1. **Why run database queries in `Task` instead of inside the button click?**
   Slow network/DB calls on the JavaFX Application Thread freeze the entire window.
2. **How does `Task` safely update the UI when it finishes?**
   `call()` runs on a background thread, while `setOnSucceeded()` and `setOnFailed()` automatically run back on the JavaFX Application Thread.
3. **Why use `ScheduledExecutorService` instead of `while (true) { Thread.sleep(5000); }`?**
   It manages a thread pool cleanly, schedules at a fixed rate, and shuts down properly when `App.stop()` is called.

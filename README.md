# Event-Driven Programming in Java

Hands-on activities, guides, and Java projects covering Event-Driven Programming, the Observer Pattern, Swing, JavaFX, and a final Capstone app.

---

## Requirements

- **Java JDK 11+** (JDK 17 or 21 recommended)
- **Maven** (needed for JavaFX in Weeks 6–10)
- Check your setup:
  ```bash
  java -version
  javac -version
  mvn -version
  ```

---

## Quick Start

### 1. Build Everything
Compile all Java files across the repo:

| Shell | Command | Clean Rebuild |
| :--- | :--- | :--- |
| **Git Bash / macOS / Linux** | `./build` | `./build --clean` |
| **Windows CMD / PowerShell** | `.\build.bat` | `.\build.bat --clean` |
| **npm (optional)** | `npm run build` | `npm run build:clean` |

### 2. Run the Interactive Menu
The easiest way to run any week's activities:

- **Git Bash / macOS / Linux:**
  ```bash
  ./scripts/run-all.sh
  ```
- **Windows PowerShell:**
  ```powershell
  .\scripts\run-all.ps1
  ```

> **Note for Git Bash users on Windows:** Always use forward slashes (`./scripts/...`), never backslashes (`.\scripts\...`).

### 3. Run a Visual Launcher Directly
For Weeks 2–5, you can open a week's GUI dashboard directly:
```bash
java -cp Week02-observer-pattern Week2Launcher
java -cp week03-swing-gui Week3Launcher
java -cp week04-event-handling Week4Launcher
java -cp week05-advanced-swing Week5Launcher
```

For Weeks 6–10 (JavaFX + Maven):
```bash
mvn -f week06-javafx/pom.xml javafx:run
mvn -f week07-javafx-events/pom.xml javafx:run
mvn -f week08-javafx-bindings/pom.xml javafx:run
mvn -f week09-async-javafx/pom.xml javafx:run
mvn -f UniversitySystem/pom.xml javafx:run
```

---

## Course Modules (Weeks 1–10)

| Week | Topic | Code Folder | Study Guide | Quick Script |
| :--- | :--- | :--- | :--- | :--- |
| **1** | Event-Driven Basics & Bug Fixes | [`Week1_Activity/`](Week1_Activity/) | [Week 1 Guide](documentation/Week%201%20Self%20paced%20Activity.md) | `./scripts/run-week1.sh` / `.\scripts\run-week1.ps1` |
| **2** | Observer Pattern | [`Week02-observer-pattern/`](Week02-observer-pattern/) | [Week 2 Guide](documentation/Week%202%20Self%20paced%20Activity.md) | `./scripts/run-week2.sh` / `.\scripts\run-week2.ps1` |
| **3** | Swing GUI & Layouts | [`week03-swing-gui/`](week03-swing-gui/) | [Week 3 Guide](documentation/Week%203%20Self%20paced%20Activity.md) | `./scripts/run-week3.sh` / `.\scripts\run-week3.ps1` |
| **4** | Event Handling in Swing | [`week04-event-handling/`](week04-event-handling/) | [Week 4 Guide](documentation/Week%204%20Self%20paced%20Activity.md) | `./scripts/run-week4.sh` / `.\scripts\run-week4.ps1` |
| **5** | Advanced Swing (`JTable`, Menus, Dialogs) | [`week05-advanced-swing/`](week05-advanced-swing/) | [Week 5 Guide](documentation/Week%205%20Advanced%20Swing%20Components.md) | `./scripts/run-week5.sh` / `.\scripts\run-week5.ps1` |
| **6** | Intro to Modern JavaFX | [`week06-javafx/`](week06-javafx/) | [Week 6 Guide](documentation/Week%206%20Self%20Paced%20Guide_Introduction%20to%20Modern%20JavaFX.md) | `./scripts/run-week6.sh` / `.\scripts\run-week6.ps1` |
| **7** | Event Handling in JavaFX | [`week07-javafx-events/`](week07-javafx-events/) | [Week 7 Guide](documentation/Week%207%20Self%20Paced%20Guide_Event%20Handling%20in%20JavaFX.md) | `./scripts/run-week7.sh` / `.\scripts\run-week7.ps1` |
| **8** | JavaFX Properties & Bindings | [`week08-javafx-bindings/`](week08-javafx-bindings/) | [Week 8 Guide](documentation/Week%208%20Self-Paced%20Guide_%20Properties%20and%20Bindings%20in%20JavaFX.md) | `./scripts/run-week8.sh` / `.\scripts\run-week8.ps1` |
| **9** | Async Programming in JavaFX | [`week09-async-javafx/`](week09-async-javafx/) | [Week 9 Guide](documentation/Week%209%20Self%20Paced%20Guide_Asynchronous%20Programming%20in%20JavaFX.md) | `./scripts/run-week9.sh` / `.\scripts\run-week9.ps1` |
| **10** | Capstone: University Management System | [`UniversitySystem/`](UniversitySystem/) | [Week 10 Guide](documentation/Week%2010%20Final%20Project%20Capstone%20-%20University%20Management%20System.md) | `./scripts/run-university.sh` / `.\scripts\run-university.ps1` |

### Script Flags
Each week script supports:
- `--all` / `-All` — Run all activities for that week
- `--launcher` / `-Launcher` — Open the visual GUI launcher (Weeks 2–9)

---

## Week 10 Capstone: University Management System

A JavaFX desktop app backed by **PostgreSQL on Neon**.

> **Note:** The University System uses **Neon (serverless cloud PostgreSQL)** instead of a local MySQL server because I wanted to try out Neon and make the app runnable without requiring a local database installation.

### Prerequisites (`.env` Setup)
1. Create a free database at [neon.tech](https://neon.tech) and copy your connection string.
2. Copy [`.env.example`](.env.example) to `.env` (or `.env.local`) in the repo root and set `DATABASE_URL`:
   ```env
   DATABASE_URL="postgresql://USER:PASSWORD@YOUR-NEON-HOST.neon.tech/neondb?sslmode=require"
   ```
   *(Tables `programs` and `students` plus sample programs are created automatically on first run.)*

### Run & Test
- **Launch the app:**
  ```bash
  mvn -f UniversitySystem/pom.xml javafx:run
  ```
  Or `npm run start:university`
- **Run database integration test:**
  ```bash
  npm run test:university
  ```

---

## Running Single Files Manually

You can also compile and run any single Java activity (Weeks 1–5) from the repo root:
```bash
javac <folder>/<FileName>.java
java -cp <folder> <MainClassName>
```

Example:
```bash
javac Week1_Activity/EventDrivenCalculator.java
java -cp Week1_Activity EventDrivenCalculator
```

---

## Author & License

- **Author:** Don Gitonga Kiriinya (`dkiriinya06sc@gmail.com`)
- **License:** [ISC License](LICENSE.md) — Copyright (c) 2026 Don Gitonga Kiriinya


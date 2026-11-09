#!/usr/bin/env bash
# University Management System Runner (Git Bash & Linux)

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

if [ "$1" == "--test" ] || [ "$1" == "-t" ]; then
    echo "Running Neon PostgreSQL End-to-End Test..."
    java -cp "$DIR/UniversitySystem/target/classes;$DIR/UniversitySystem/lib/postgresql-42.7.3.jar" com.university.UniversitySystemTest
    exit 0
fi

echo "Launching University Management System (JavaFX + Neon PostgreSQL)..."
mvn -f "$DIR/UniversitySystem/pom.xml" javafx:run

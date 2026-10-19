#!/usr/bin/env bash
# Week 2 Observer Design Pattern Runner (Git Bash & Linux)

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
WEEK2_DIR="$DIR/Week02-observer-pattern"

run_app() {
    local folder="$1"
    local class="$2"
    local name="$3"
    echo -e "\033[33m=== $name ===\033[0m"
    (cd "$DIR" && java -cp "$folder" "$class")
    echo ""
}

case "$1" in
    --all|-a)
        echo -e "\033[32mRunning all Week 2 observer pattern console demos...\033[0m\n"
        run_app "$WEEK2_DIR/activity1-weather-station" "WeatherStationMain" "1. Weather Station Simulation"
        run_app "$WEEK2_DIR/activity2-legacy-observer" "LegacyMain" "2. Legacy Observer Demo"
        run_app "$WEEK2_DIR/activity3-custom-observer" "TightlyCoupledSystem" "3a. Tightly Coupled System"
        run_app "$WEEK2_DIR/activity3-custom-observer" "RefactoredMain" "3b. Refactored Custom Observer Pattern"
        run_app "$WEEK2_DIR/activity4-comparison" "PropertyChangeDemo" "4. PropertyChangeListener Demo"
        exit 0
        ;;
esac

clear
echo -e "\033[36m==========================================================\033[0m"
echo -e "\033[33m         WEEK 2: OBSERVER DESIGN PATTERN RUNNER           \033[0m"
echo -e "\033[36m==========================================================\033[0m"
echo -e " \033[35m[A]\033[0m  Run ALL Week 2 console demos sequentially"
echo -e "----------------------------------------------------------"
echo " [1]  Weather Station App (Observer Pattern)"
echo " [2]  Legacy Weather App (Built-in java.util.Observer)"
echo " [3a] Tightly Coupled System (Anti-Pattern Baseline)"
echo " [3b] Refactored Custom Observer Pattern (Clean Decoupling)"
echo " [4]  PropertyChangeListener Modern Alternative"
echo " [Q]  Quit"
echo -e "\033[36m==========================================================\033[0m"

read -p "Select an option: " choice

case "${choice^^}" in
    A)
        run_app "$WEEK2_DIR/activity1-weather-station" "WeatherStationMain" "Weather Station Simulation"
        run_app "$WEEK2_DIR/activity2-legacy-observer" "LegacyMain" "Legacy Observer Demo"
        run_app "$WEEK2_DIR/activity3-custom-observer" "TightlyCoupledSystem" "Tightly Coupled System"
        run_app "$WEEK2_DIR/activity3-custom-observer" "RefactoredMain" "Refactored Custom Observer Pattern"
        run_app "$WEEK2_DIR/activity4-comparison" "PropertyChangeDemo" "PropertyChangeListener Demo"
        ;;
    1)  run_app "$WEEK2_DIR/activity1-weather-station" "WeatherStationMain" "Weather Station Simulation" ;;
    2)  run_app "$WEEK2_DIR/activity2-legacy-observer" "LegacyMain" "Legacy Observer Demo" ;;
    3A) run_app "$WEEK2_DIR/activity3-custom-observer" "TightlyCoupledSystem" "Tightly Coupled System" ;;
    3B) run_app "$WEEK2_DIR/activity3-custom-observer" "RefactoredMain" "Refactored Custom Observer Pattern" ;;
    4)  run_app "$WEEK2_DIR/activity4-comparison" "PropertyChangeDemo" "PropertyChangeListener Demo" ;;
    Q)  echo "Exiting." ;;
    *)  echo "Invalid choice '$choice'." ;;
esac

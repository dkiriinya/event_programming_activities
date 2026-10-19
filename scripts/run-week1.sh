#!/usr/bin/env bash
# Week 1 Activity Runner (Git Bash & Linux)

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
WEEK1_DIR="$DIR/Week1_Activity"
BUG_DIR="$WEEK1_DIR/BugFixChallenge"

run_app() {
    local folder="$1"
    local class="$2"
    local name="$3"
    local is_cli="$4"
    echo -e "\033[36mRunning $name...\033[0m"
    if [ "$is_cli" = "true" ]; then
        (cd "$DIR" && java -cp "$folder" "$class")
    else
        (cd "$DIR" && java -cp "$folder" "$class") &
    fi
}

launch_all() {
    echo -e "\033[32mLaunching all Week 1 activities...\033[0m"
    run_app "$WEEK1_DIR" "EventDrivenCalculator" "Event-Driven Calculator" false
    run_app "$WEEK1_DIR" "SmartHomePrototype" "Smart Home Prototype" false
    run_app "$BUG_DIR" "Bug1_BlockingEDT" "Bug 1: Blocking EDT" false
    run_app "$BUG_DIR" "Bug2_MissingListener" "Bug 2: Missing Listener" false
    run_app "$BUG_DIR" "Bug3_WrongThread" "Bug 3: Wrong Thread" false
    run_app "$BUG_DIR" "Bug4_MemoryLeak" "Bug 4: Memory Leak" false
    echo -e "\033[32mAll Week 1 GUI activities launched!\033[0m"
}

case "$1" in
    --all|-a)
        launch_all
        exit 0
        ;;
esac

clear
echo -e "\033[36m==========================================================\033[0m"
echo -e "\033[33m     WEEK 1: PROCEDURAL VS EVENT-DRIVEN PROGRAMMING       \033[0m"
echo -e "\033[36m==========================================================\033[0m"
echo -e " \033[35m[A]\033[0m  Launch ALL Week 1 activities at once"
echo -e "----------------------------------------------------------"
echo " [1]  Procedural Console Calculator (CLI)"
echo " [2]  Event-Driven Swing Calculator (GUI)"
echo " [3]  Smart Home Prototype (GUI)"
echo " [4a] Bug 1: Blocking EDT Challenge (GUI)"
echo " [4b] Bug 2: Missing Listener Challenge (GUI)"
echo " [4c] Bug 3: Wrong Thread Challenge (GUI)"
echo " [4d] Bug 4: Memory Leak Challenge (GUI)"
echo " [Q]  Quit"
echo -e "\033[36m==========================================================\033[0m"

read -p "Select an option: " choice

case "${choice^^}" in
    A)  launch_all ;;
    1)  run_app "$WEEK1_DIR" "ProceduralCalculator" "Procedural Calculator" true ;;
    2)  run_app "$WEEK1_DIR" "EventDrivenCalculator" "EventDrivenCalculator" false ;;
    3)  run_app "$WEEK1_DIR" "SmartHomePrototype" "SmartHomePrototype" false ;;
    4A) run_app "$BUG_DIR" "Bug1_BlockingEDT" "Bug1_BlockingEDT" false ;;
    4B) run_app "$BUG_DIR" "Bug2_MissingListener" "Bug2_MissingListener" false ;;
    4C) run_app "$BUG_DIR" "Bug3_WrongThread" "Bug3_WrongThread" false ;;
    4D) run_app "$BUG_DIR" "Bug4_MemoryLeak" "Bug4_MemoryLeak" false ;;
    Q)  echo "Exiting." ;;
    *)  echo "Invalid choice '$choice'." ;;
esac

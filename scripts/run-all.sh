#!/usr/bin/env bash
# Global Master Activity Runner (Git Bash & Linux)

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

case "$1" in
    1|--week1)
        bash "$DIR/run-week1.sh" "$2"
        exit 0
        ;;
    2|--week2)
        bash "$DIR/run-week2.sh" "$2"
        exit 0
        ;;
    3|--week3)
        bash "$DIR/run-week3.sh" "$2"
        exit 0
        ;;
    4|--week4)
        bash "$DIR/run-week4.sh" "$2"
        exit 0
        ;;
    5|--week5)
        bash "$DIR/run-week5.sh" "$2"
        exit 0
        ;;
esac

clear
echo -e "\033[36m==========================================================\033[0m"
echo -e "\033[33m    EVENT-DRIVEN PROGRAMMING IN JAVA: MASTER RUNNER       \033[0m"
echo -e "\033[36m==========================================================\033[0m"
echo " Select a week to explore and run its activities:"
echo ""
echo " [1]  Week 1: Fundamentals (Procedural vs Event-Driven, Bug Fixes)"
echo " [2]  Week 2: Observer Design Pattern (Weather Station & Custom)"
echo " [3]  Week 3: Swing GUI Layouts, EDT Thread Safety & Student Portal"
echo -e " \033[32m[4]\033[0m  Week 4: Interactive Swing Event Handling (Listeners & Shortcuts)"
echo -e " \033[32m[5]\033[0m  Week 5: Advanced Swing Components (JTable, JTree, File Manager)"
echo "----------------------------------------------------------"
echo " Quick GUI Dashboards:"
echo " [G3] Week 3 Master GUI Dashboard"
echo -e " \033[32m[G4]\033[0m Week 4 Master GUI Dashboard"
echo -e " \033[32m[G5]\033[0m Week 5 Master GUI Dashboard"
echo "----------------------------------------------------------"
echo " [Q]  Quit"
echo -e "\033[36m==========================================================\033[0m"

read -p "Select an option: " choice

case "${choice^^}" in
    1)  bash "$DIR/run-week1.sh" ;;
    2)  bash "$DIR/run-week2.sh" ;;
    3)  bash "$DIR/run-week3.sh" ;;
    4)  bash "$DIR/run-week4.sh" ;;
    5)  bash "$DIR/run-week5.sh" ;;
    G3) bash "$DIR/run-week3.sh" --launcher ;;
    G4) bash "$DIR/run-week4.sh" --launcher ;;
    G5) bash "$DIR/run-week5.sh" --launcher ;;
    Q)  echo "Exiting." ;;
    *)  echo "Invalid choice '$choice'." ;;
esac

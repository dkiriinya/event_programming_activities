#!/usr/bin/env bash
# Week 3 Swing GUI Basics Runner (Git Bash & Linux)

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
WEEK3_DIR="$DIR/week03-swing-gui"

run_app() {
    local folder="$1"
    local class="$2"
    local name="$3"
    echo -e "\033[36mLaunching $name...\033[0m"
    (cd "$DIR" && java -cp "$WEEK3_DIR/$folder" "$class") &
}

launch_all() {
    echo -e "\033[32mLaunching ALL 9 Week 3 Activities Concurrently...\033[0m"
    run_app "activity1-first-window" "FirstWindow" "1. First Swing Window"
    run_app "activity2-basic-components" "ComponentShowcase" "2. Component Showcase"
    run_app "activity3-layout-managers" "FlowLayoutDemo" "3a. FlowLayout Demo"
    run_app "activity3-layout-managers" "BorderLayoutDemo" "3b. BorderLayout Demo"
    run_app "activity3-layout-managers" "GridLayoutDemo" "3c. GridLayout Demo"
    run_app "activity3-layout-managers" "CombinedLayoutDemo" "3d. Combined Layout Demo"
    run_app "activity4-edt-rule" "FreezeDemo" "4a. EDT Freeze Demo"
    run_app "activity4-edt-rule" "FixedDemo" "4b. EDT Fixed Demo"
    run_app "activity5-complete-mockup" "StudentPortal" "5. Complete Student Portal"
    echo -e "\033[32mAll 9 activities launched!\033[0m"
}

launch_launcher() {
    echo -e "\033[32mOpening Master GUI Dashboard (Week3Launcher)...\033[0m"
    (cd "$DIR" && java -cp "$WEEK3_DIR" Week3Launcher) &
}

case "$1" in
    --all|-a)
        launch_all
        exit 0
        ;;
    --launcher|-g)
        launch_launcher
        exit 0
        ;;
esac

clear
echo -e "\033[36m==========================================================\033[0m"
echo -e "\033[33m     WEEK 3: SWING GUI COMPONENTS, LAYOUTS & EDT RUNNER  \033[0m"
echo -e "\033[36m==========================================================\033[0m"
echo -e " \033[32m[G]\033[0m  Open Master GUI Dashboard (Week3Launcher)"
echo -e " \033[35m[A]\033[0m  Launch ALL 9 activities at once"
echo -e "----------------------------------------------------------"
echo " [1]  First Swing Window (EDT InvokeLater)"
echo " [2]  Swing Component Showcase"
echo " [3a] FlowLayout Demo"
echo " [3b] BorderLayout Demo"
echo " [3c] GridLayout Demo"
echo " [3d] Combined & Nested Layouts Demo"
echo " [4a] EDT Freeze Demo (Anti-Pattern)"
echo " [4b] EDT Fixed Demo (SwingWorker Thread-Safety)"
echo " [5]  Complete Student Portal GUI Mockup"
echo " [Q]  Quit"
echo -e "\033[36m==========================================================\033[0m"

read -p "Select an option: " choice

case "${choice^^}" in
    G)  launch_launcher ;;
    A)  launch_all ;;
    1)  run_app "activity1-first-window" "FirstWindow" "FirstWindow" ;;
    2)  run_app "activity2-basic-components" "ComponentShowcase" "ComponentShowcase" ;;
    3A) run_app "activity3-layout-managers" "FlowLayoutDemo" "FlowLayoutDemo" ;;
    3B) run_app "activity3-layout-managers" "BorderLayoutDemo" "BorderLayoutDemo" ;;
    3C) run_app "activity3-layout-managers" "GridLayoutDemo" "GridLayoutDemo" ;;
    3D) run_app "activity3-layout-managers" "CombinedLayoutDemo" "CombinedLayoutDemo" ;;
    4A) run_app "activity4-edt-rule" "FreezeDemo" "FreezeDemo" ;;
    4B) run_app "activity4-edt-rule" "FixedDemo" "FixedDemo" ;;
    5)  run_app "activity5-complete-mockup" "StudentPortal" "StudentPortal" ;;
    Q)  echo "Exiting." ;;
    *)  echo "Invalid choice '$choice'." ;;
esac

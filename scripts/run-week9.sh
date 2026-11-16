#!/usr/bin/env bash
# Week 9 Asynchronous Programming in JavaFX Runner (Git Bash & Linux)

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
WEEK9_DIR="$DIR/week09-async-javafx"

get_openjfx_module_path() {
    local m2_dir="$HOME/.m2/repository/org/openjfx"
    if [ -d "$m2_dir" ]; then
        local jars
        jars=$(find "$m2_dir" -name "*.jar" | tr '\n' ':' | sed 's/:$//')
        echo "$jars"
    fi
}

run_app() {
    local class="$1"
    local name="$2"
    echo -e "\033[36mLaunching $name...\033[0m"
    local jfx_path
    jfx_path=$(get_openjfx_module_path)
    local target_classes="$WEEK9_DIR/target/classes"

    if [ -d "$target_classes" ] && [ -n "$jfx_path" ]; then
        (cd "$WEEK9_DIR" && java --module-path "$jfx_path" --add-modules javafx.controls,javafx.fxml -cp "$target_classes" "$class") &
    else
        (cd "$WEEK9_DIR" && mvn javafx:run -Djavafx.mainClass="$class") &
    fi
}

launch_all() {
    echo -e "\033[32mLaunching ALL 4 Week 9 Activities Concurrently...\033[0m"
    run_app "com.example.FreezeDemo" "1. The Freeze Demo"
    run_app "com.example.TaskDemo" "2. Task Demo"
    run_app "com.example.ProgressDemo" "3. Progress Bar Demo"
    run_app "com.example.ServiceDemo" "4. Service Demo"
    echo -e "\033[32mAll 4 activities launched!\033[0m"
}

launch_launcher() {
    echo -e "\033[32mOpening Master JavaFX Dashboard (Week9Launcher)...\033[0m"
    run_app "com.example.Week9Launcher" "Week 9 Master Dashboard"
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
    1)
        run_app "com.example.FreezeDemo" "FreezeDemo"
        exit 0
        ;;
    2)
        run_app "com.example.TaskDemo" "TaskDemo"
        exit 0
        ;;
    3)
        run_app "com.example.ProgressDemo" "ProgressDemo"
        exit 0
        ;;
    4)
        run_app "com.example.ServiceDemo" "ServiceDemo"
        exit 0
        ;;
esac

clear
echo -e "\033[36m==========================================================\033[0m"
echo -e "\033[33m     WEEK 9: ASYNCHRONOUS PROGRAMMING IN JAVAFX           \033[0m"
echo -e "\033[36m==========================================================\033[0m"
echo -e " \033[32m[G]\033[0m  Open Master JavaFX Dashboard (Week9Launcher)"
echo -e " \033[35m[A]\033[0m  Launch ALL 4 activities at once"
echo -e "----------------------------------------------------------"
echo " [1]  The Freeze (Blocking UI Thread)"
echo " [2]  The Fix (Task & Platform.runLater)"
echo " [3]  Live Progress (ProgressBar Binding)"
echo " [4]  The Service Class (DataSyncService)"
echo " [Q]  Quit"
echo -e "\033[36m==========================================================\033[0m"

read -p "Select an option: " choice

case "${choice^^}" in
    G)  launch_launcher ;;
    A)  launch_all ;;
    1)  run_app "com.example.FreezeDemo" "FreezeDemo" ;;
    2)  run_app "com.example.TaskDemo" "TaskDemo" ;;
    3)  run_app "com.example.ProgressDemo" "ProgressDemo" ;;
    4)  run_app "com.example.ServiceDemo" "ServiceDemo" ;;
    Q)  echo "Exiting." ;;
    *)  echo "Invalid choice '$choice'." ;;
esac

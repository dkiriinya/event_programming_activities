#!/usr/bin/env bash
# Week 7 JavaFX Event Handling Runner (Git Bash & Linux)

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
WEEK7_DIR="$DIR/week07-javafx-events"

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
    local target_classes="$WEEK7_DIR/target/classes"

    if [ -d "$target_classes" ] && [ -n "$jfx_path" ]; then
        (cd "$WEEK7_DIR" && java --module-path "$jfx_path" --add-modules javafx.controls,javafx.fxml -cp "$target_classes" "$class") &
    else
        (cd "$WEEK7_DIR" && mvn javafx:run -Djavafx.mainClass="$class") &
    fi
}

launch_all() {
    echo -e "\033[32mLaunching ALL 4 Week 7 Activities Concurrently...\033[0m"
    run_app "com.example.EventPropagationDemo" "1. Event Propagation"
    run_app "com.example.MouseKeyDemo" "2. Mouse and Key Events"
    run_app "com.example.DragDropDemo" "3. Drag and Drop"
    run_app "com.example.CustomEventDemo" "4. Custom Events"
    echo -e "\033[32mAll 4 activities launched!\033[0m"
}

launch_launcher() {
    echo -e "\033[32mOpening Master JavaFX Dashboard (Week7Launcher)...\033[0m"
    run_app "com.example.Week7Launcher" "Week 7 Master Dashboard"
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
        run_app "com.example.EventPropagationDemo" "EventPropagationDemo"
        exit 0
        ;;
    2)
        run_app "com.example.MouseKeyDemo" "MouseKeyDemo"
        exit 0
        ;;
    3)
        run_app "com.example.DragDropDemo" "DragDropDemo"
        exit 0
        ;;
    4)
        run_app "com.example.CustomEventDemo" "CustomEventDemo"
        exit 0
        ;;
esac

clear
echo -e "\033[36m==========================================================\033[0m"
echo -e "\033[33m       WEEK 7: JAVAFX EVENT HANDLING RUNNER               \033[0m"
echo -e "\033[36m==========================================================\033[0m"
echo -e " \033[32m[G]\033[0m  Open Master JavaFX Dashboard (Week7Launcher)"
echo -e " \033[35m[A]\033[0m  Launch ALL 4 activities at once"
echo -e "----------------------------------------------------------"
echo " [1]  Event Propagation (Filters vs Handlers)"
echo " [2]  Mouse and Key Events"
echo " [3]  Drag and Drop Interaction"
echo " [4]  Custom Events (LoginSuccessEvent)"
echo " [Q]  Quit"
echo -e "\033[36m==========================================================\033[0m"

read -p "Select an option: " choice

case "${choice^^}" in
    G)  launch_launcher ;;
    A)  launch_all ;;
    1)  run_app "com.example.EventPropagationDemo" "EventPropagationDemo" ;;
    2)  run_app "com.example.MouseKeyDemo" "MouseKeyDemo" ;;
    3)  run_app "com.example.DragDropDemo" "DragDropDemo" ;;
    4)  run_app "com.example.CustomEventDemo" "CustomEventDemo" ;;
    Q)  echo "Exiting." ;;
    *)  echo "Invalid choice '$choice'." ;;
esac

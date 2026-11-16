#!/usr/bin/env bash
# Week 8 Properties and Bindings in JavaFX Runner (Git Bash & Linux)

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
WEEK8_DIR="$DIR/week08-javafx-bindings"

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
    local target_classes="$WEEK8_DIR/target/classes"
    local src_classes="$WEEK8_DIR/src/main/java"

    if [ -d "$target_classes" ] && [ -n "$jfx_path" ]; then
        (cd "$WEEK8_DIR" && java --module-path "$jfx_path" --add-modules javafx.controls,javafx.fxml -cp "$target_classes" "$class") &
    elif [ -d "$src_classes" ] && [ -n "$jfx_path" ]; then
        (cd "$WEEK8_DIR" && java --module-path "$jfx_path" --add-modules javafx.controls,javafx.fxml -cp "$src_classes" "$class") &
    else
        (cd "$WEEK8_DIR" && mvn javafx:run -Djavafx.mainClass="$class") &
    fi
}

launch_all() {
    echo -e "\033[32mLaunching ALL 4 Week 8 Activities Concurrently...\033[0m"
    run_app "com.example.ListenerDemo" "1 & 2. Observable Properties & Listeners"
    run_app "com.example.UnidirectionalDemo" "3. Unidirectional Binding"
    run_app "com.example.BidirectionalDemo" "4. Bidirectional Binding"
    run_app "com.example.ReactiveFormApp" "5. Capstone Reactive Registration Form"
    echo -e "\033[32mAll 4 activities launched!\033[0m"
}

launch_launcher() {
    echo -e "\033[32mOpening Master JavaFX Dashboard (Week8Launcher)...\033[0m"
    run_app "com.example.Week8Launcher" "Week 8 Master Dashboard"
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
        run_app "com.example.ListenerDemo" "ListenerDemo"
        exit 0
        ;;
    2)
        run_app "com.example.UnidirectionalDemo" "UnidirectionalDemo"
        exit 0
        ;;
    3)
        run_app "com.example.BidirectionalDemo" "BidirectionalDemo"
        exit 0
        ;;
    4)
        run_app "com.example.ReactiveFormApp" "ReactiveFormApp"
        exit 0
        ;;
esac

clear
echo -e "\033[36m==========================================================\033[0m"
echo -e "\033[33m       WEEK 8: JAVAFX PROPERTIES & BINDINGS RUNNER        \033[0m"
echo -e "\033[36m==========================================================\033[0m"
echo -e " \033[32m[G]\033[0m  Open Master JavaFX Dashboard (Week8Launcher)"
echo -e " \033[35m[A]\033[0m  Launch ALL 4 activities at once"
echo -e "----------------------------------------------------------"
echo " [1]  Observable Properties & Listeners (Person + ListenerDemo)"
echo " [2]  Unidirectional Binding (UnidirectionalDemo)"
echo " [3]  Bidirectional Binding (BidirectionalDemo)"
echo " [4]  Capstone: Reactive Registration Form (ReactiveFormApp)"
echo " [Q]  Quit"
echo -e "\033[36m==========================================================\033[0m"

read -p "Select an option: " choice

case "${choice^^}" in
    G)  launch_launcher ;;
    A)  launch_all ;;
    1)  run_app "com.example.ListenerDemo" "ListenerDemo" ;;
    2)  run_app "com.example.UnidirectionalDemo" "UnidirectionalDemo" ;;
    3)  run_app "com.example.BidirectionalDemo" "BidirectionalDemo" ;;
    4)  run_app "com.example.ReactiveFormApp" "ReactiveFormApp" ;;
    Q)  echo "Exiting." ;;
    *)  echo "Invalid choice '$choice'." ;;
esac

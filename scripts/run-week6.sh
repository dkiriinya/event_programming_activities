#!/usr/bin/env bash
# Week 6 Modern JavaFX Runner (Git Bash & Linux)

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
WEEK6_DIR="$DIR/week06-javafx"
CLASSES_DIR="$WEEK6_DIR/target/classes"

# Determine OS for JavaFX native jars
if [[ "$OSTYPE" == "msys"* || "$OSTYPE" == "cygwin"* || "$OSTYPE" == "win32"* ]]; then
    LIB_DIR="$WEEK6_DIR/lib/win"
else
    LIB_DIR="$WEEK6_DIR/lib/linux"
fi

run_app() {
    local class="$1"
    local name="$2"
    echo -e "\033[36mLaunching $name...\033[0m"
    if [ -d "$LIB_DIR" ] && [ -d "$CLASSES_DIR" ]; then
        (cd "$WEEK6_DIR" && java --module-path "$LIB_DIR" --add-modules javafx.controls,javafx.fxml -cp "$CLASSES_DIR" "$class") &
    else
        (cd "$WEEK6_DIR" && ./mvnw javafx:run -Djavafx.mainClass="$class") &
    fi
}

launch_all() {
    echo -e "\033[32mLaunching ALL Week 6 JavaFX Activities Concurrently...\033[0m"
    run_app "programmaticdemo.Main" "1. Programmatic Hello World"
    sleep 0.3
    run_app "programmaticdemo.LayoutDemo" "2. JavaFX Layout Panes"
    sleep 0.3
    run_app "programmaticdemo.FXMLApp" "3 & 4. FXML Login & Controller"
    sleep 0.3
    run_app "programmaticdemo.DashboardApp" "5. Capstone SaaS Dashboard"
    echo -e "\033[32mAll activities launched!\033[0m"
}

launch_launcher() {
    echo -e "\033[32mOpening Master GUI Dashboard (Week6Launcher)...\033[0m"
    (cd "$WEEK6_DIR" && java -cp "$WEEK6_DIR" Week6Launcher) &
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
echo -e "\033[33m       WEEK 6: INTRODUCTION TO MODERN JAVAFX RUNNER      \033[0m"
echo -e "\033[36m==========================================================\033[0m"
echo -e " \033[32m[G]\033[0m  Open Master GUI Dashboard (Week6Launcher)"
echo -e " \033[35m[A]\033[0m  Launch ALL activities at once"
echo -e "----------------------------------------------------------"
echo " [1]  Programmatic Hello World (Stage, Scene, Node)"
echo " [2]  JavaFX Layouts (HBox, VBox, GridPane, BorderPane)"
echo " [3]  Scene Builder & FXML UI Form (login.fxml)"
echo " [4]  FXML Controller & Event Wiring"
echo " [5]  Capstone SaaS Dashboard Application"
echo " [Q]  Quit"
echo -e "\033[36m==========================================================\033[0m"

read -p "Select an option: " choice

case "${choice^^}" in
    G)  launch_launcher ;;
    A)  launch_all ;;
    1)  run_app "programmaticdemo.Main" "Main (Hello World)" ;;
    2)  run_app "programmaticdemo.LayoutDemo" "LayoutDemo" ;;
    3|4) run_app "programmaticdemo.FXMLApp" "FXMLApp (Login Form)" ;;
    5)  run_app "programmaticdemo.DashboardApp" "DashboardApp (SaaS Dashboard)" ;;
    Q)  echo "Exiting." ;;
    *)  echo "Invalid choice '$choice'." ;;
esac

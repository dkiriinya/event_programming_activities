#!/usr/bin/env bash
# Week 4 Interactive Swing Activity Runner (Git Bash & Linux)

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
WEEK4_DIR="$DIR/week04-event-handling"

run_app() {
    local folder="$1"
    local class="$2"
    local name="$3"
    echo -e "\033[36mLaunching $name...\033[0m"
    (cd "$DIR" && java -cp "$WEEK4_DIR/$folder" "$class") &
}

launch_all() {
    echo -e "\033[32mLaunching ALL 10 Week 4 Activities Concurrently...\033[0m"
    run_app "activity1-actionlistener-basics" "FirstButton" "1. First Interactive Button"
    run_app "activity2-listener-techniques" "ListenerDemo" "2a. Listener Implementation Techniques"
    run_app "activity2-listener-techniques" "EventSourceDemo" "2b. Event Source Identification"
    run_app "activity3-item-and-document-listeners" "ItemListenerDemo" "3a. Pizza Order Form"
    run_app "activity3-item-and-document-listeners" "DocumentListenerDemo" "3b. Live Character Counter"
    run_app "activity3-item-and-document-listeners" "LiveSearchDemo" "3c. Live Search Filter"
    run_app "activity4-counter-app" "CounterApp" "4. Interactive Counter App"
    run_app "activity5-interactive-form" "RegistrationForm" "5. Interactive Registration Form"
    run_app "activity6-mouse-and-key" "DrawingApp" "6a. Drawing Canvas"
    run_app "activity6-mouse-and-key" "KeyboardMover" "6b. Keyboard Mover"
    echo -e "\033[32mAll 10 activities launched!\033[0m"
}

launch_launcher() {
    echo -e "\033[32mOpening Master GUI Dashboard (Week4Launcher)...\033[0m"
    (cd "$DIR" && java -cp "$WEEK4_DIR" Week4Launcher) &
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
echo -e "\033[33m     WEEK 4: EVENT HANDLING & SWING INTERACTIVE RUNNER    \033[0m"
echo -e "\033[36m==========================================================\033[0m"
echo -e " \033[32m[G]\033[0m  Open Master GUI Dashboard (Week4Launcher)"
echo -e " \033[35m[A]\033[0m  Launch ALL 10 activities at once"
echo -e "----------------------------------------------------------"
echo " [1]  First Interactive Button (Lambda)"
echo " [2a] Listener Implementation Techniques"
echo " [2b] Event Source Identification (e.getSource)"
echo " [3a] Pizza Order Form (ItemListener)"
echo " [3b] Live Character Counter (DocumentListener)"
echo " [3c] Live Search Filter (DocumentListener)"
echo " [4]  Interactive Counter App with Shortcuts"
echo " [5]  Interactive Registration Form (Validation)"
echo " [6a] Drawing Canvas (MouseMotionListener)"
echo " [6b] Keyboard Mover (KeyListener)"
echo " [Q]  Quit"
echo -e "\033[36m==========================================================\033[0m"

read -p "Select an option: " choice

case "${choice^^}" in
    G)
        launch_launcher
        ;;
    A)
        launch_all
        ;;
    1)
        run_app "activity1-actionlistener-basics" "FirstButton" "FirstButton"
        ;;
    2A)
        run_app "activity2-listener-techniques" "ListenerDemo" "ListenerDemo"
        ;;
    2B)
        run_app "activity2-listener-techniques" "EventSourceDemo" "EventSourceDemo"
        ;;
    3A)
        run_app "activity3-item-and-document-listeners" "ItemListenerDemo" "ItemListenerDemo"
        ;;
    3B)
        run_app "activity3-item-and-document-listeners" "DocumentListenerDemo" "DocumentListenerDemo"
        ;;
    3C)
        run_app "activity3-item-and-document-listeners" "LiveSearchDemo" "LiveSearchDemo"
        ;;
    4)
        run_app "activity4-counter-app" "CounterApp" "CounterApp"
        ;;
    5)
        run_app "activity5-interactive-form" "RegistrationForm" "RegistrationForm"
        ;;
    6A)
        run_app "activity6-mouse-and-key" "DrawingApp" "DrawingApp"
        ;;
    6B)
        run_app "activity6-mouse-and-key" "KeyboardMover" "KeyboardMover"
        ;;
    Q)
        echo "Exiting."
        ;;
    *)
        echo "Invalid choice '$choice'."
        ;;
esac

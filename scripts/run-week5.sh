#!/usr/bin/env bash
# Week 5 Advanced Swing Runner (Git Bash & Linux)

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
WEEK5_DIR="$DIR/week05-advanced-swing"

run_app() {
    local folder="$1"
    local class="$2"
    local name="$3"
    echo -e "\033[36mLaunching $name...\033[0m"
    (cd "$DIR" && java -cp "$WEEK5_DIR/$folder" "$class") &
}

launch_all() {
    echo -e "\033[32mLaunching ALL 5 Week 5 Activities Concurrently...\033[0m"
    run_app "activity1-jlist-renderer" "ContactListDemo" "1. Custom JList Cell Renderer"
    run_app "activity2-jtable" "StudentTableDemo" "2. Dynamic JTable"
    run_app "activity3-jtree-tabs" "FileExplorerDemo" "3. JTree & JTabbedPane Document Viewer"
    run_app "activity4-menus-dialogs" "MiniEditor" "4. Menu Bars & Dialogs (MiniEditor)"
    run_app "activity5-capstone-file-manager" "FileManager" "5. Capstone Desktop File Manager"
    echo -e "\033[32mAll 5 activities launched!\033[0m"
}

launch_launcher() {
    echo -e "\033[32mOpening Master GUI Dashboard (Week5Launcher)...\033[0m"
    (cd "$DIR" && java -cp "$WEEK5_DIR" Week5Launcher) &
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
echo -e "\033[33m       WEEK 5: ADVANCED SWING COMPONENTS RUNNER           \033[0m"
echo -e "\033[36m==========================================================\033[0m"
echo -e " \033[32m[G]\033[0m  Open Master GUI Dashboard (Week5Launcher)"
echo -e " \033[35m[A]\033[0m  Launch ALL 5 activities at once"
echo -e "----------------------------------------------------------"
echo " [1]  Custom JList Cell Renderer (Contact List)"
echo " [2]  Dynamic JTable with Sorting & Filtering"
echo " [3]  JTree & JTabbedPane Document Viewer"
echo " [4]  Menu Bars, Accelerators & Dialogs (MiniEditor)"
echo " [5]  Capstone Desktop File Manager Application"
echo " [Q]  Quit"
echo -e "\033[36m==========================================================\033[0m"

read -p "Select an option: " choice

case "${choice^^}" in
    G)  launch_launcher ;;
    A)  launch_all ;;
    1)  run_app "activity1-jlist-renderer" "ContactListDemo" "ContactListDemo" ;;
    2)  run_app "activity2-jtable" "StudentTableDemo" "StudentTableDemo" ;;
    3)  run_app "activity3-jtree-tabs" "FileExplorerDemo" "FileExplorerDemo" ;;
    4)  run_app "activity4-menus-dialogs" "MiniEditor" "MiniEditor" ;;
    5)  run_app "activity5-capstone-file-manager" "FileManager" "FileManager" ;;
    Q)  echo "Exiting." ;;
    *)  echo "Invalid choice '$choice'." ;;
esac

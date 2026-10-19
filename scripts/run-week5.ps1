<#
.SYNOPSIS
    Week 5 Activity Runner — Advanced Swing Components
.DESCRIPTION
    Runs any or all Week 5 activities (JList Renderers, JTable, JTree, Menus, Desktop File Manager).
.EXAMPLE
    .\scripts\run-week5.ps1             # Interactive menu
    .\scripts\run-week5.ps1 -All        # Spawns all 5 activities simultaneously
    .\scripts\run-week5.ps1 -Launcher   # Opens the Master GUI Dashboard
    .\scripts\run-week5.ps1 -Activity 5 # Runs FileManager Capstone
#>

param(
    [switch]$All,
    [switch]$Launcher,
    [string]$Activity
)

$baseDir = Split-Path -Parent $PSScriptRoot
$week5Dir = Join-Path $baseDir "week05-advanced-swing"

$activities = @(
    @{ Id = "1"; Name = "Custom JList Cell Renderer (Contact List)"; Folder = "activity1-jlist-renderer"; Class = "ContactListDemo" },
    @{ Id = "2"; Name = "Dynamic JTable with Sorting & Filtering"; Folder = "activity2-jtable"; Class = "StudentTableDemo" },
    @{ Id = "3"; Name = "JTree & JTabbedPane Document Viewer"; Folder = "activity3-jtree-tabs"; Class = "FileExplorerDemo" },
    @{ Id = "4"; Name = "Menu Bars, Accelerators & Dialogs (MiniEditor)"; Folder = "activity4-menus-dialogs"; Class = "MiniEditor" },
    @{ Id = "5"; Name = "Capstone Desktop File Manager Application"; Folder = "activity5-capstone-file-manager"; Class = "FileManager" }
)

function Start-ActivityApp($act) {
    $classPath = Join-Path $week5Dir $act.Folder
    Write-Host "Launching [$($act.Id)] $($act.Name)..." -ForegroundColor Cyan
    Start-Process java -ArgumentList "-cp", "`"$classPath`"", $act.Class
}

if ($Launcher) {
    Write-Host "Launching Week 5 Master GUI Dashboard..." -ForegroundColor Green
    Start-Process java -ArgumentList "-cp", "`"$week5Dir`"", "Week5Launcher"
    exit
}

if ($All) {
    Write-Host "Launching ALL 5 Week 5 Activities..." -ForegroundColor Green
    foreach ($act in $activities) {
        Start-ActivityApp $act
        Start-Sleep -Milliseconds 250
    }
    Write-Host "All activities launched in separate windows!" -ForegroundColor Green
    exit
}

if ($Activity) {
    $match = $activities | Where-Object { $_.Id -eq $Activity }
    if ($match) {
        Start-ActivityApp $match
        exit
    } else {
        Write-Warning "Activity ID '$Activity' not found. Available IDs: $($activities.Id -join ', ')"
    }
}

Clear-Host
Write-Host "==========================================================" -ForegroundColor Cyan
Write-Host "       WEEK 5: ADVANCED SWING COMPONENTS RUNNER           " -ForegroundColor Yellow
Write-Host "==========================================================" -ForegroundColor Cyan
Write-Host " [G]  Open Master GUI Dashboard (Week5Launcher)" -ForegroundColor Green
Write-Host " [A]  Launch ALL 5 activities at once" -ForegroundColor Magenta
Write-Host "----------------------------------------------------------"
foreach ($act in $activities) {
    Write-Host " [$($act.Id.PadRight(2))] $($act.Name)"
}
Write-Host " [Q]  Quit"
Write-Host "==========================================================" -ForegroundColor Cyan

$choice = Read-Host "Select an option"

switch ($choice.ToUpper().Trim()) {
    "G" {
        Start-Process java -ArgumentList "-cp", "`"$week5Dir`"", "Week5Launcher"
    }
    "A" {
        foreach ($act in $activities) {
            Start-ActivityApp $act
            Start-Sleep -Milliseconds 250
        }
    }
    "Q" {
        Write-Host "Exiting."
    }
    Default {
        $selected = $activities | Where-Object { $_.Id -eq $choice.Trim() }
        if ($selected) {
            Start-ActivityApp $selected
        } else {
            Write-Warning "Invalid choice '$choice'."
        }
    }
}

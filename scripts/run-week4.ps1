<#
.SYNOPSIS
    Week 4 Activity Runner — Interactive Swing Event Handling
.DESCRIPTION
    Runs any or all Week 4 interactive event-handling activities.
.EXAMPLE
    .\scripts\run-week4.ps1             # Interactive menu
    .\scripts\run-week4.ps1 -All        # Spawns all 10 activities simultaneously
    .\scripts\run-week4.ps1 -Launcher   # Opens the Master GUI Dashboard
    .\scripts\run-week4.ps1 -Activity 4 # Runs CounterApp
#>

param(
    [switch]$All,
    [switch]$Launcher,
    [string]$Activity
)

$baseDir = Split-Path -Parent $PSScriptRoot
$week4Dir = Join-Path $baseDir "week04-event-handling"

$activities = @(
    @{ Id = "1"; Name = "First Interactive Button (Lambda)"; Folder = "activity1-actionlistener-basics"; Class = "FirstButton" },
    @{ Id = "2a"; Name = "Listener Implementation Techniques"; Folder = "activity2-listener-techniques"; Class = "ListenerDemo" },
    @{ Id = "2b"; Name = "Event Source Identification (e.getSource)"; Folder = "activity2-listener-techniques"; Class = "EventSourceDemo" },
    @{ Id = "3a"; Name = "Pizza Order Form (ItemListener)"; Folder = "activity3-item-and-document-listeners"; Class = "ItemListenerDemo" },
    @{ Id = "3b"; Name = "Live Character Counter (DocumentListener)"; Folder = "activity3-item-and-document-listeners"; Class = "DocumentListenerDemo" },
    @{ Id = "3c"; Name = "Live Search Filter (DocumentListener)"; Folder = "activity3-item-and-document-listeners"; Class = "LiveSearchDemo" },
    @{ Id = "4"; Name = "Interactive Counter App with Shortcuts"; Folder = "activity4-counter-app"; Class = "CounterApp" },
    @{ Id = "5"; Name = "Interactive Registration Form (Validation)"; Folder = "activity5-interactive-form"; Class = "RegistrationForm" },
    @{ Id = "6a"; Name = "Drawing Canvas (MouseMotionListener)"; Folder = "activity6-mouse-and-key"; Class = "DrawingApp" },
    @{ Id = "6b"; Name = "Keyboard Mover (KeyListener)"; Folder = "activity6-mouse-and-key"; Class = "KeyboardMover" }
)

function Start-ActivityApp($act) {
    $classPath = Join-Path $week4Dir $act.Folder
    Write-Host "Launching [$($act.Id)] $($act.Name)..." -ForegroundColor Cyan
    Start-Process java -ArgumentList "-cp", "`"$classPath`"", $act.Class
}

if ($Launcher) {
    Write-Host "Launching Week 4 Master GUI Dashboard..." -ForegroundColor Green
    Start-Process java -ArgumentList "-cp", "`"$week4Dir`"", "Week4Launcher"
    exit
}

if ($All) {
    Write-Host "Launching ALL 10 Week 4 Activities..." -ForegroundColor Green
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

# Interactive Menu
Clear-Host
Write-Host "==========================================================" -ForegroundColor Cyan
Write-Host "     WEEK 4: EVENT HANDLING & SWING INTERACTIVE RUNNER    " -ForegroundColor Yellow
Write-Host "==========================================================" -ForegroundColor Cyan
Write-Host " [G]  Open Master GUI Dashboard (Week4Launcher)" -ForegroundColor Green
Write-Host " [A]  Launch ALL 10 activities at once" -ForegroundColor Magenta
Write-Host "----------------------------------------------------------"
foreach ($act in $activities) {
    Write-Host " [$($act.Id.PadRight(2))] $($act.Name)"
}
Write-Host " [Q]  Quit"
Write-Host "==========================================================" -ForegroundColor Cyan

$choice = Read-Host "Select an option"

switch ($choice.ToUpper().Trim()) {
    "G" {
        Start-Process java -ArgumentList "-cp", "`"$week4Dir`"", "Week4Launcher"
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

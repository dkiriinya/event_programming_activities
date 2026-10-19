<#
.SYNOPSIS
    Week 3 Activity Runner — Swing GUI Basics, Layouts, EDT & Student Portal
.DESCRIPTION
    Runs any or all Week 3 activities.
.EXAMPLE
    .\scripts\run-week3.ps1             # Interactive menu
    .\scripts\run-week3.ps1 -All        # Spawns all 9 activities simultaneously
    .\scripts\run-week3.ps1 -Launcher   # Opens the Master GUI Dashboard
    .\scripts\run-week3.ps1 -Activity 5 # Runs StudentPortal
#>

param(
    [switch]$All,
    [switch]$Launcher,
    [string]$Activity
)

$baseDir = Split-Path -Parent $PSScriptRoot
$week3Dir = Join-Path $baseDir "week03-swing-gui"

$activities = @(
    @{ Id = "1"; Name = "First Swing Window (EDT InvokeLater)"; Folder = "activity1-first-window"; Class = "FirstWindow" },
    @{ Id = "2"; Name = "Swing Component Showcase"; Folder = "activity2-basic-components"; Class = "ComponentShowcase" },
    @{ Id = "3a"; Name = "FlowLayout Demo"; Folder = "activity3-layout-managers"; Class = "FlowLayoutDemo" },
    @{ Id = "3b"; Name = "BorderLayout Demo"; Folder = "activity3-layout-managers"; Class = "BorderLayoutDemo" },
    @{ Id = "3c"; Name = "GridLayout Demo"; Folder = "activity3-layout-managers"; Class = "GridLayoutDemo" },
    @{ Id = "3d"; Name = "Combined & Nested Layouts Demo"; Folder = "activity3-layout-managers"; Class = "CombinedLayoutDemo" },
    @{ Id = "4a"; Name = "EDT Freeze Demo (Anti-Pattern)"; Folder = "activity4-edt-rule"; Class = "FreezeDemo" },
    @{ Id = "4b"; Name = "EDT Fixed Demo (SwingWorker Thread-Safety)"; Folder = "activity4-edt-rule"; Class = "FixedDemo" },
    @{ Id = "5"; Name = "Complete Student Portal GUI Mockup"; Folder = "activity5-complete-mockup"; Class = "StudentPortal" }
)

function Start-ActivityApp($act) {
    $classPath = Join-Path $week3Dir $act.Folder
    Write-Host "Launching [$($act.Id)] $($act.Name)..." -ForegroundColor Cyan
    Start-Process java -ArgumentList "-cp", "`"$classPath`"", $act.Class
}

if ($Launcher) {
    Write-Host "Launching Week 3 Master GUI Dashboard..." -ForegroundColor Green
    Start-Process java -ArgumentList "-cp", "`"$week3Dir`"", "Week3Launcher"
    exit
}

if ($All) {
    Write-Host "Launching ALL 9 Week 3 Activities..." -ForegroundColor Green
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
Write-Host "     WEEK 3: SWING GUI COMPONENTS, LAYOUTS & EDT RUNNER  " -ForegroundColor Yellow
Write-Host "==========================================================" -ForegroundColor Cyan
Write-Host " [G]  Open Master GUI Dashboard (Week3Launcher)" -ForegroundColor Green
Write-Host " [A]  Launch ALL 9 activities at once" -ForegroundColor Magenta
Write-Host "----------------------------------------------------------"
foreach ($act in $activities) {
    Write-Host " [$($act.Id.PadRight(2))] $($act.Name)"
}
Write-Host " [Q]  Quit"
Write-Host "==========================================================" -ForegroundColor Cyan

$choice = Read-Host "Select an option"

switch ($choice.ToUpper().Trim()) {
    "G" {
        Start-Process java -ArgumentList "-cp", "`"$week3Dir`"", "Week3Launcher"
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

<#
.SYNOPSIS
    Week 6 Activity Runner — Introduction to Modern JavaFX
.DESCRIPTION
    Runs any or all Week 6 activities (Hello World, Layout Panes, Scene Builder FXML, Controller Wiring, SaaS Dashboard).
.EXAMPLE
    .\scripts\run-week6.ps1             # Interactive menu
    .\scripts\run-week6.ps1 -All        # Spawns all activities simultaneously
    .\scripts\run-week6.ps1 -Launcher   # Opens the Master GUI Dashboard (Week6Launcher)
    .\scripts\run-week6.ps1 -Activity 5 # Runs Capstone Dashboard
#>

param(
    [switch]$All,
    [switch]$Launcher,
    [string]$Activity
)

$baseDir = Split-Path -Parent $PSScriptRoot
$week6Dir = Join-Path $baseDir "week06-javafx"
$classesDir = Join-Path $week6Dir "target\classes"
$libDir = Join-Path $week6Dir "lib\win"

$activities = @(
    @{ Id = "1"; Name = "Programmatic Hello World (Stage, Scene, Node)"; Class = "programmaticdemo.Main" },
    @{ Id = "2"; Name = "JavaFX Layouts (HBox, VBox, GridPane, BorderPane)"; Class = "programmaticdemo.LayoutDemo" },
    @{ Id = "3"; Name = "Scene Builder & FXML UI Form (login.fxml)"; Class = "programmaticdemo.FXMLApp" },
    @{ Id = "4"; Name = "FXML Controller & Event Wiring"; Class = "programmaticdemo.FXMLApp" },
    @{ Id = "5"; Name = "Capstone SaaS Dashboard Application"; Class = "programmaticdemo.DashboardApp" }
)

function Start-ActivityApp($act) {
    Write-Host "Launching [$($act.Id)] $($act.Name)..." -ForegroundColor Cyan
    if ((Test-Path $libDir) -and (Test-Path $classesDir)) {
        Start-Process java -ArgumentList "--module-path", "`"$libDir`"", "--add-modules", "javafx.controls,javafx.fxml", "-cp", "`"$classesDir`"", $act.Class
    } else {
        $mvnw = Join-Path $week6Dir "mvnw.cmd"
        if (Test-Path $mvnw) {
            Start-Process $mvnw -ArgumentList "javafx:run", "-Djavafx.mainClass=$($act.Class)" -WorkingDirectory $week6Dir
        } else {
            Start-Process mvn -ArgumentList "javafx:run", "-Djavafx.mainClass=$($act.Class)" -WorkingDirectory $week6Dir
        }
    }
}

if ($Launcher) {
    Write-Host "Launching Week 6 Master GUI Dashboard..." -ForegroundColor Green
    Start-Process java -ArgumentList "-cp", "`"$week6Dir`"", "Week6Launcher" -WorkingDirectory $week6Dir
    exit
}

if ($All) {
    Write-Host "Launching ALL Week 6 Activities..." -ForegroundColor Green
    # Launch distinct applications
    $distinct = @("1", "2", "3", "5")
    foreach ($id in $distinct) {
        $act = $activities | Where-Object { $_.Id -eq $id }
        Start-ActivityApp $act
        Start-Sleep -Milliseconds 350
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
Write-Host "       WEEK 6: INTRODUCTION TO MODERN JAVAFX RUNNER      " -ForegroundColor Yellow
Write-Host "==========================================================" -ForegroundColor Cyan
Write-Host " [G]  Open Master GUI Dashboard (Week6Launcher)" -ForegroundColor Green
Write-Host " [A]  Launch ALL activities at once" -ForegroundColor Magenta
Write-Host "----------------------------------------------------------"
foreach ($act in $activities) {
    Write-Host " [$($act.Id.PadRight(2))] $($act.Name)"
}
Write-Host " [Q]  Quit"
Write-Host "==========================================================" -ForegroundColor Cyan

$choice = Read-Host "Select an option"

switch ($choice.ToUpper().Trim()) {
    "G" {
        Start-Process java -ArgumentList "-cp", "`"$week6Dir`"", "Week6Launcher" -WorkingDirectory $week6Dir
    }
    "A" {
        $distinct = @("1", "2", "3", "5")
        foreach ($id in $distinct) {
            $act = $activities | Where-Object { $_.Id -eq $id }
            Start-ActivityApp $act
            Start-Sleep -Milliseconds 350
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

<#
.SYNOPSIS
    Week 7 Activity Runner — JavaFX Event Handling
.DESCRIPTION
    Runs any or all Week 7 JavaFX activities (Event Propagation, Mouse & Key Events, Drag and Drop, Custom Events).
.EXAMPLE
    .\scripts\run-week7.ps1             # Interactive menu
    .\scripts\run-week7.ps1 -All        # Spawns all 4 activities simultaneously
    .\scripts\run-week7.ps1 -Launcher   # Opens the JavaFX Master GUI Dashboard
    .\scripts\run-week7.ps1 -Activity 1 # Runs Activity 1 (EventPropagationDemo)
#>

param(
    [switch]$All,
    [switch]$Launcher,
    [string]$Activity
)

$baseDir = Split-Path -Parent $PSScriptRoot
$week7Dir = Join-Path $baseDir "week07-javafx-events"

$activities = @(
    @{ Id = "1"; Name = "Event Propagation (Filters vs Handlers)"; Class = "com.example.EventPropagationDemo"; Desc = "Capturing vs bubbling phases, e.consume()" },
    @{ Id = "2"; Name = "Mouse and Key Events"; Class = "com.example.MouseKeyDemo"; Desc = "Real-time mouse coordinates & keyboard movement" },
    @{ Id = "3"; Name = "Drag and Drop Interaction"; Class = "com.example.DragDropDemo"; Desc = "Dragboard, TransferMode.MOVE & drop feedback" },
    @{ Id = "4"; Name = "Custom Events (LoginSuccessEvent)"; Class = "com.example.CustomEventDemo"; Desc = "Decoupled Event & EventType dispatch" }
)

function Get-OpenJFXPath {
    $m2Dir = "$env:USERPROFILE\.m2\repository\org\openjfx"
    if (Test-Path $m2Dir) {
        $jars = Get-ChildItem -Path $m2Dir -Recurse -Filter "*.jar" | Select-Object -ExpandProperty FullName
        if ($jars) {
            return ($jars -join ";")
        }
    }
    return $null
}

function Start-ActivityApp($act) {
    Write-Host "Launching [$($act.Id)] $($act.Name)..." -ForegroundColor Cyan
    $jfxPath = Get-OpenJFXPath
    $targetClasses = Join-Path $week7Dir "target\classes"

    if (Test-Path $targetClasses) {
        if ($jfxPath) {
            Start-Process java -ArgumentList "--module-path", "`"$jfxPath`"", "--add-modules", "javafx.controls,javafx.fxml", "-cp", "`"$targetClasses`"", $act.Class
            return
        }
    }
    # Fallback to Maven
    Start-Process mvn -ArgumentList "javafx:run", "-Djavafx.mainClass=$($act.Class)" -WorkingDirectory $week7Dir
}

if ($Launcher) {
    Write-Host "Launching Week 7 Master JavaFX Dashboard..." -ForegroundColor Green
    $act = @{ Id = "G"; Name = "Week 7 Launcher"; Class = "com.example.Week7Launcher" }
    Start-ActivityApp $act
    exit
}

if ($All) {
    Write-Host "Launching ALL 4 Week 7 Activities..." -ForegroundColor Green
    foreach ($act in $activities) {
        Start-ActivityApp $act
        Start-Sleep -Milliseconds 400
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
Write-Host "       WEEK 7: JAVAFX EVENT HANDLING RUNNER               " -ForegroundColor Yellow
Write-Host "==========================================================" -ForegroundColor Cyan
Write-Host " [G]  Open Master JavaFX Dashboard (Week7Launcher)" -ForegroundColor Green
Write-Host " [A]  Launch ALL 4 activities at once" -ForegroundColor Magenta
Write-Host "----------------------------------------------------------"
foreach ($act in $activities) {
    Write-Host " [$($act.Id.PadRight(2))] $($act.Name)"
}
Write-Host " [Q]  Quit"
Write-Host "==========================================================" -ForegroundColor Cyan

$choice = Read-Host "Select an option"

switch ($choice.ToUpper().Trim()) {
    "G" {
        $act = @{ Id = "G"; Name = "Week 7 Launcher"; Class = "com.example.Week7Launcher" }
        Start-ActivityApp $act
    }
    "A" {
        foreach ($act in $activities) {
            Start-ActivityApp $act
            Start-Sleep -Milliseconds 400
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

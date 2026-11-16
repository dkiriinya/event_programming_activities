<#
.SYNOPSIS
    Week 9 Activity Runner — Asynchronous Programming in JavaFX
.DESCRIPTION
    Runs any or all Week 9 JavaFX concurrency activities (FreezeDemo, TaskDemo, ProgressDemo, ServiceDemo).
.EXAMPLE
    .\scripts\run-week9.ps1             # Interactive menu
    .\scripts\run-week9.ps1 -All        # Spawns all 4 activities simultaneously
    .\scripts\run-week9.ps1 -Launcher   # Opens the JavaFX Master GUI Dashboard
    .\scripts\run-week9.ps1 -Activity 1 # Runs Activity 1 (FreezeDemo)
#>

param(
    [switch]$All,
    [switch]$Launcher,
    [string]$Activity
)

$baseDir = Split-Path -Parent $PSScriptRoot
$week9Dir = Join-Path $baseDir "week09-async-javafx"

$activities = @(
    @{ Id = "1"; Name = "The Freeze (Blocking UI Thread)"; Class = "com.example.FreezeDemo"; Desc = "Thread.sleep() inside event handler freezes UI" },
    @{ Id = "2"; Name = "The Fix (Task & Platform.runLater)"; Class = "com.example.TaskDemo"; Desc = "Background Task with safe UI thread updates" },
    @{ Id = "3"; Name = "Live Progress (ProgressBar Binding)"; Class = "com.example.ProgressDemo"; Desc = "updateProgress(), updateMessage(), and unbind()" },
    @{ Id = "4"; Name = "The Service Class (DataSyncService)"; Class = "com.example.ServiceDemo"; Desc = "Restartable & cancellable Service with spinner" }
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
    $targetClasses = Join-Path $week9Dir "target\classes"

    if (Test-Path $targetClasses) {
        if ($jfxPath) {
            Start-Process java -ArgumentList "--module-path", "`"$jfxPath`"", "--add-modules", "javafx.controls,javafx.fxml", "-cp", "`"$targetClasses`"", $act.Class
            return
        }
    }
    # Fallback to Maven
    Start-Process mvn -ArgumentList "javafx:run", "-Djavafx.mainClass=$($act.Class)" -WorkingDirectory $week9Dir
}

if ($Launcher) {
    Write-Host "Launching Week 9 Master JavaFX Dashboard..." -ForegroundColor Green
    $act = @{ Id = "G"; Name = "Week 9 Launcher"; Class = "com.example.Week9Launcher" }
    Start-ActivityApp $act
    exit
}

if ($All) {
    Write-Host "Launching ALL 4 Week 9 Activities..." -ForegroundColor Green
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
Write-Host "     WEEK 9: ASYNCHRONOUS PROGRAMMING IN JAVAFX           " -ForegroundColor Yellow
Write-Host "==========================================================" -ForegroundColor Cyan
Write-Host " [G]  Open Master JavaFX Dashboard (Week9Launcher)" -ForegroundColor Green
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
        $act = @{ Id = "G"; Name = "Week 9 Launcher"; Class = "com.example.Week9Launcher" }
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

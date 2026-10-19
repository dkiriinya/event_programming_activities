<#
.SYNOPSIS
    Week 2 Activity Runner — Observer Design Pattern & PropertyChangeListener
.DESCRIPTION
    Runs any or all Week 2 activities.
.EXAMPLE
    .\scripts\run-week2.ps1             # Interactive menu
    .\scripts\run-week2.ps1 -All        # Runs all activities
    .\scripts\run-week2.ps1 -Activity 1 # Runs WeatherStationMain
#>

param(
    [switch]$All,
    [string]$Activity
)

$baseDir = Split-Path -Parent $PSScriptRoot
$week2Dir = Join-Path $baseDir "Week02-observer-pattern"

$activities = @(
    @{ Id = "1"; Name = "Weather Station App (Observer Pattern)"; Folder = Join-Path $week2Dir "activity1-weather-station"; Class = "WeatherStationMain" },
    @{ Id = "2"; Name = "Legacy Weather App (Built-in java.util.Observer)"; Folder = Join-Path $week2Dir "activity2-legacy-observer"; Class = "LegacyMain" },
    @{ Id = "3a"; Name = "Tightly Coupled System (Anti-Pattern Baseline)"; Folder = Join-Path $week2Dir "activity3-custom-observer"; Class = "TightlyCoupledSystem" },
    @{ Id = "3b"; Name = "Refactored Custom Observer Pattern (Clean Decoupling)"; Folder = Join-Path $week2Dir "activity3-custom-observer"; Class = "RefactoredMain" },
    @{ Id = "4"; Name = "PropertyChangeListener Modern Alternative"; Folder = Join-Path $week2Dir "activity4-comparison"; Class = "PropertyChangeDemo" }
)

function Start-ActivityApp($act) {
    Write-Host "Launching [$($act.Id)] $($act.Name)..." -ForegroundColor Cyan
    Start-Process powershell -ArgumentList "-NoExit", "-Command", "Write-Host '=== $($act.Name) ===' -ForegroundColor Yellow; java -cp `"$($act.Folder)`" $($act.Class)"
}

if ($All) {
    Write-Host "Launching all Week 2 activities..." -ForegroundColor Green
    foreach ($act in $activities) {
        Start-ActivityApp $act
        Start-Sleep -Milliseconds 250
    }
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
Write-Host "         WEEK 2: OBSERVER DESIGN PATTERN RUNNER           " -ForegroundColor Yellow
Write-Host "==========================================================" -ForegroundColor Cyan
Write-Host " [A]  Launch ALL Week 2 activities at once" -ForegroundColor Magenta
Write-Host "----------------------------------------------------------"
foreach ($act in $activities) {
    Write-Host " [$($act.Id.PadRight(2))] $($act.Name)"
}
Write-Host " [Q]  Quit"
Write-Host "==========================================================" -ForegroundColor Cyan

$choice = Read-Host "Select an option"

switch ($choice.ToUpper().Trim()) {
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

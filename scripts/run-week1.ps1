<#
.SYNOPSIS
    Week 1 Activity Runner — Procedural vs Event-Driven & Bug Fix Challenges
.DESCRIPTION
    Runs any or all Week 1 activities.
.EXAMPLE
    .\scripts\run-week1.ps1             # Interactive menu
    .\scripts\run-week1.ps1 -All        # Spawns all activities
    .\scripts\run-week1.ps1 -Activity 2 # Runs EventDrivenCalculator
#>

param(
    [switch]$All,
    [string]$Activity
)

$baseDir = Split-Path -Parent $PSScriptRoot
$week1Dir = Join-Path $baseDir "Week1_Activity"
$bugDir = Join-Path $week1Dir "BugFixChallenge"

$activities = @(
    @{ Id = "1"; Name = "Procedural Console Calculator (CLI)"; Folder = $week1Dir; Class = "ProceduralCalculator"; IsCli = $true },
    @{ Id = "2"; Name = "Event-Driven Swing Calculator (GUI)"; Folder = $week1Dir; Class = "EventDrivenCalculator"; IsCli = $false },
    @{ Id = "3"; Name = "Smart Home Prototype (GUI)"; Folder = $week1Dir; Class = "SmartHomePrototype"; IsCli = $false },
    @{ Id = "4a"; Name = "Bug 1: Blocking EDT Challenge (GUI)"; Folder = $bugDir; Class = "Bug1_BlockingEDT"; IsCli = $false },
    @{ Id = "4b"; Name = "Bug 2: Missing Listener Challenge (GUI)"; Folder = $bugDir; Class = "Bug2_MissingListener"; IsCli = $false },
    @{ Id = "4c"; Name = "Bug 3: Wrong Thread Challenge (GUI)"; Folder = $bugDir; Class = "Bug3_WrongThread"; IsCli = $false },
    @{ Id = "4d"; Name = "Bug 4: Memory Leak Challenge (GUI)"; Folder = $bugDir; Class = "Bug4_MemoryLeak"; IsCli = $false }
)

function Start-ActivityApp($act) {
    Write-Host "Launching [$($act.Id)] $($act.Name)..." -ForegroundColor Cyan
    if ($act.IsCli) {
        Start-Process powershell -ArgumentList "-NoExit", "-Command", "Write-Host '=== Running $($act.Name) ===' -ForegroundColor Yellow; java -cp `"$($act.Folder)`" $($act.Class)"
    } else {
        Start-Process java -ArgumentList "-cp", "`"$($act.Folder)`"", $act.Class
    }
}

if ($All) {
    Write-Host "Launching all Week 1 activities..." -ForegroundColor Green
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
Write-Host "     WEEK 1: PROCEDURAL VS EVENT-DRIVEN PROGRAMMING       " -ForegroundColor Yellow
Write-Host "==========================================================" -ForegroundColor Cyan
Write-Host " [A]  Launch ALL Week 1 activities at once" -ForegroundColor Magenta
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

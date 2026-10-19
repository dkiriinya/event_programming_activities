<#
.SYNOPSIS
    Global Activity Runner — Event-Driven Programming in Java (Weeks 1 to 5)
.DESCRIPTION
    Unified master launcher for all weeks and activities.
.EXAMPLE
    .\scripts\run-all.ps1             # Main interactive menu
    .\scripts\run-all.ps1 -Week 4     # Open Week 4 runner
    .\scripts\run-all.ps1 -Week 5     # Open Week 5 runner
#>

param(
    [ValidateSet("1","2","3","4","5")]
    [string]$Week
)

$scriptDir = $PSScriptRoot

if ($Week) {
    & (Join-Path $scriptDir "run-week$Week.ps1")
    exit
}

Clear-Host
Write-Host "==========================================================" -ForegroundColor Cyan
Write-Host "    EVENT-DRIVEN PROGRAMMING IN JAVA: MASTER RUNNER       " -ForegroundColor Yellow
Write-Host "==========================================================" -ForegroundColor Cyan
Write-Host " Select a week to explore and run its activities:"
Write-Host ""
Write-Host " [1]  Week 1: Fundamentals (Procedural vs Event-Driven, Bug Fixes)"
Write-Host " [2]  Week 2: Observer Design Pattern (Weather Station & Custom)"
Write-Host " [3]  Week 3: Swing GUI Layouts, EDT Thread Safety & Student Portal"
Write-Host " [4]  Week 4: Interactive Swing Event Handling (Listeners & Shortcuts)" -ForegroundColor Green
Write-Host " [5]  Week 5: Advanced Swing Components (JTable, JTree, File Manager)" -ForegroundColor Green
Write-Host "----------------------------------------------------------"
Write-Host " Quick GUI Dashboards:"
Write-Host " [G3] Week 3 Master GUI Dashboard"
Write-Host " [G4] Week 4 Master GUI Dashboard" -ForegroundColor Green
Write-Host " [G5] Week 5 Master GUI Dashboard" -ForegroundColor Green
Write-Host "----------------------------------------------------------"
Write-Host " [Q]  Quit"
Write-Host "==========================================================" -ForegroundColor Cyan

$choice = Read-Host "Select an option"

switch ($choice.ToUpper().Trim()) {
    "1"  { & (Join-Path $scriptDir "run-week1.ps1") }
    "2"  { & (Join-Path $scriptDir "run-week2.ps1") }
    "3"  { & (Join-Path $scriptDir "run-week3.ps1") }
    "4"  { & (Join-Path $scriptDir "run-week4.ps1") }
    "5"  { & (Join-Path $scriptDir "run-week5.ps1") }
    "G3" { & (Join-Path $scriptDir "run-week3.ps1") -Launcher }
    "G4" { & (Join-Path $scriptDir "run-week4.ps1") -Launcher }
    "G5" { & (Join-Path $scriptDir "run-week5.ps1") -Launcher }
    "Q"  { Write-Host "Exiting." }
    Default { Write-Warning "Invalid choice '$choice'." }
}

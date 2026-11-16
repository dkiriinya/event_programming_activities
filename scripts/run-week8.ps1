<#
.SYNOPSIS
    Week 8 Activity Runner — Properties and Bindings in JavaFX
.DESCRIPTION
    Runs any or all Week 8 JavaFX activities (Observable Properties, Listeners, Unidirectional & Bidirectional Bindings, Reactive Form Capstone).
.EXAMPLE
    .\scripts\run-week8.ps1             # Interactive menu
    .\scripts\run-week8.ps1 -All        # Spawns all 4 activities simultaneously
    .\scripts\run-week8.ps1 -Launcher   # Opens the JavaFX Master GUI Dashboard
    .\scripts\run-week8.ps1 -Activity 1 # Runs Activity 1 & 2 (ListenerDemo)
#>

param(
    [switch]$All,
    [switch]$Launcher,
    [string]$Activity
)

$baseDir = Split-Path -Parent $PSScriptRoot
$week8Dir = Join-Path $baseDir "week08-javafx-bindings"

$activities = @(
    @{ Id = "1"; Name = "Observable Properties & Listeners (Person + ListenerDemo)"; Class = "com.example.ListenerDemo"; Desc = "InvalidationListener vs ChangeListener on StringProperty" },
    @{ Id = "2"; Name = "Unidirectional Binding (UnidirectionalDemo)"; Class = "com.example.UnidirectionalDemo"; Desc = "One-way binding from TextField to Label" },
    @{ Id = "3"; Name = "Bidirectional Binding (BidirectionalDemo)"; Class = "com.example.BidirectionalDemo"; Desc = "Two-way Slider <-> TextField binding with NumberStringConverter" },
    @{ Id = "4"; Name = "Capstone: Reactive Registration Form (ReactiveFormApp)"; Class = "com.example.ReactiveFormApp"; Desc = "Live char counter, preview, and declarative validation" }
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
    $targetClasses = Join-Path $week8Dir "target\classes"
    $srcClasses = Join-Path $week8Dir "src\main\java"

    $cpDir = if (Test-Path $targetClasses) { $targetClasses } else { $srcClasses }
    if (Test-Path $cpDir) {
        if ($jfxPath) {
            Start-Process java -ArgumentList "--module-path", "`"$jfxPath`"", "--add-modules", "javafx.controls,javafx.fxml", "-cp", "`"$cpDir`"", $act.Class
            return
        }
    }
    # Fallback to Maven
    Start-Process mvn -ArgumentList "javafx:run", "-Djavafx.mainClass=$($act.Class)" -WorkingDirectory $week8Dir
}

if ($Launcher) {
    Write-Host "Launching Week 8 Master JavaFX Dashboard..." -ForegroundColor Green
    $act = @{ Id = "G"; Name = "Week 8 Launcher"; Class = "com.example.Week8Launcher" }
    Start-ActivityApp $act
    exit
}

if ($All) {
    Write-Host "Launching ALL 4 Week 8 Activities..." -ForegroundColor Green
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
Write-Host "       WEEK 8: JAVAFX PROPERTIES & BINDINGS RUNNER        " -ForegroundColor Yellow
Write-Host "==========================================================" -ForegroundColor Cyan
Write-Host " [G]  Open Master JavaFX Dashboard (Week8Launcher)" -ForegroundColor Green
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
        $act = @{ Id = "G"; Name = "Week 8 Launcher"; Class = "com.example.Week8Launcher" }
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

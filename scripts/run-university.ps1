<#
.SYNOPSIS
    University Management System Runner (Final Project Capstone)
.DESCRIPTION
    Launches the JavaFX University Management System connected to Neon PostgreSQL.
.EXAMPLE
    .\scripts\run-university.ps1
    .\scripts\run-university.ps1 -Test    # Runs headless integration test
#>

param(
    [switch]$Test,
    [switch]$Seed
)

$baseDir = Split-Path -Parent $PSScriptRoot
$uniDir = Join-Path $baseDir "UniversitySystem"

if ($Seed) {
    Write-Host "Seeding 100 Kenyan Student Records into Neon PostgreSQL..." -ForegroundColor Cyan
    $cp = "$uniDir\target\classes;$uniDir\lib\postgresql-42.7.3.jar"
    java -cp $cp com.university.SeedStudents
    exit
}

if ($Test) {
    Write-Host "Running Neon PostgreSQL End-to-End Test..." -ForegroundColor Cyan
    $cp = "$uniDir\target\classes;$uniDir\lib\postgresql-42.7.3.jar"
    java -cp $cp com.university.UniversitySystemTest
    exit
}

Write-Host "Launching University Management System (JavaFX + Neon PostgreSQL)..." -ForegroundColor Cyan
mvn -f (Join-Path $uniDir "pom.xml") javafx:run

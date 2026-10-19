@echo off
rem Build pipeline runner for Windows CMD
java -cp "%~dp0" AgentCLI build %*

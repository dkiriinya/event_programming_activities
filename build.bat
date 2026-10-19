@echo off
rem Root build command for Windows (similar to 'npm run build')
java -cp "%~dp0scripts" AgentCLI build %*

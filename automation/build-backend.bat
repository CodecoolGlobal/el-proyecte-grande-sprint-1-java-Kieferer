@echo off
set backendAutomationPath=%~dp0
cd /d "%backendAutomationPath%\.."
CALL mvn -N wrapper:wrapper
CALL docker-compose up --build
@echo off
set backendAutomationPath=%~dp0
cd /d "%backendAutomationPath%\.."
CALL docker-compose up --build
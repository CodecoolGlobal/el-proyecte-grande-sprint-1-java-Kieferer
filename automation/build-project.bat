@echo off
set prefix=[BudapestGo2]:
echo %prefix% Build application
echo - frontend [IN-PROGRESS]
REM Frontend build and copy to backend resources
CALL build-frontend.bat
cls

REM Re-print previous messages and additionals
echo %prefix% Build application
echo - frontend [DONE]

echo - backend  [IN-PROGRESS]
REM Backend build, dockerize jar
CALL build-backend.bat
cls

REM Re-print previous messages and additionals
echo %prefix% Build application
echo - frontend [DONE]
echo - backend  [DONE]
pause

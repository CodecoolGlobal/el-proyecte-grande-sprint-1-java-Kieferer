@echo off

set "currentFolder=%~dp0"

set "parentFolder=%currentFolder:~0,-1%"
cd /d "%parentFolder%\.."

echo Current folder: %CD%
pause
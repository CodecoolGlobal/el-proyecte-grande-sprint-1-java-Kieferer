@echo off
set frontendPath=${ROOT_FRONTEND}
set frontendBuildPath=%frontendPath%\budapest-go-2-frontend\build
set backendAutomationPath=%~dp0

:Validate
IF "%frontendPath%"=="" (
    echo Frontend root varaible is empty.
    pause
    exit
) ELSE (
    REM Check if the path root exists
    IF EXIST %frontendPath% (
        echo - frontend folder is exsists
        REM Check if the path root is accessible
        dir %frontendPath% >nul 2>nul
        IF %errorlevel% equ 0 (
            echo -frontend folder is accessible.
        ) ELSE (
            echo Given frontend folder is not accessible.
            pause
            exit
        )
    ) ELSE (
        echo Given frontend folder does not exist.
        pause
        exit
    )
)

:Update
echo - change cd to frontend folder
cd %frontendPath%
echo - change git branch to main
git checkout main
echo - pull changes from repository
git pull

:Build
cd %frontendPath%\budapest-go-2-frontend
call npm i
call npm run build

cd /d "%backendAutomationPath%\.."
cd src\main\resources
rmdir /s /q frontend
mkdir frontend
xcopy /E /I %frontendBuildPath% frontend
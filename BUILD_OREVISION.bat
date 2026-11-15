@echo off
setlocal
title OreVision - Build

cd /d "%~dp0"

echo ========================================
echo          OreVision Mod Builder
echo ========================================
echo.

where java >nul 2>nul
if errorlevel 1 (
    echo ERROR: Java was not found.
    echo Install Java 21 or newer, then try again.
    echo.
    pause
    exit /b 1
)

set "GRADLE_VERSION=9.5.0"
set "GRADLE_HOME=%~dp0.gradle\gradle-%GRADLE_VERSION%"
set "GRADLE_ZIP=%~dp0.gradle\gradle-%GRADLE_VERSION%-bin.zip"

if not exist "%GRADLE_HOME%\bin\gradle.bat" (
    echo Gradle is not installed in this project yet.
    echo Downloading Gradle %GRADLE_VERSION%...
    if not exist "%~dp0.gradle" mkdir "%~dp0.gradle"

    powershell -NoProfile -ExecutionPolicy Bypass -Command ^
      "$ProgressPreference='SilentlyContinue'; Invoke-WebRequest -Uri 'https://services.gradle.org/distributions/gradle-%GRADLE_VERSION%-bin.zip' -OutFile '%GRADLE_ZIP%'"
    if errorlevel 1 (
        echo.
        echo ERROR: Gradle download failed. Check your internet connection.
        pause
        exit /b 1
    )

    echo Extracting Gradle...
    powershell -NoProfile -ExecutionPolicy Bypass -Command ^
      "Expand-Archive -LiteralPath '%GRADLE_ZIP%' -DestinationPath '%~dp0.gradle' -Force"
    if errorlevel 1 (
        echo.
        echo ERROR: Could not extract Gradle.
        pause
        exit /b 1
    )
    del "%GRADLE_ZIP%" >nul 2>nul
)

echo.
echo Building OreVision...
echo This may take a few minutes the first time.
echo.

call "%GRADLE_HOME%\bin\gradle.bat" --no-daemon build
if errorlevel 1 (
    echo.
    echo BUILD FAILED. Copy the error text above and send it to ChatGPT.
    pause
    exit /b 1
)

echo.
echo ========================================
echo BUILD SUCCESSFUL!
echo Your mod JAR should be in:
echo build\libs\
echo ========================================
echo.
explorer "%~dp0build\libs"
pause

@echo off
REM Gatewayz Banking System - JavaFX Launcher (Windows)
REM 
REM REQUIREMENTS:
REM 1. Java 11+ installed
REM 2. JavaFX SDK downloaded from: https://openjfx.io/
REM
REM SETUP:
REM Set JAVAFX_HOME to your JavaFX SDK path, or edit the path below

setlocal

REM Try to find JavaFX
if defined JAVAFX_HOME (
    set "JAVAFX_PATH=%JAVAFX_HOME%\lib"
    goto :found
)

if exist "%USERPROFILE%\javafx-sdk-21\lib" (
    set "JAVAFX_PATH=%USERPROFILE%\javafx-sdk-21\lib"
    goto :found
)

if exist "%USERPROFILE%\javafx-sdk-21.0.9\lib" (
    set "JAVAFX_PATH=%USERPROFILE%\javafx-sdk-21.0.9\lib"
    goto :found
)

if exist "C:\Program Files\javafx-sdk\lib" (
    set "JAVAFX_PATH=C:\Program Files\javafx-sdk\lib"
    goto :found
)

echo JavaFX SDK not found!
echo.
echo Please download JavaFX SDK from: https://openjfx.io/
echo Then either:
echo   1. Set JAVAFX_HOME environment variable
echo   2. Extract to %%USERPROFILE%%\javafx-sdk-21\
echo.
pause
exit /b 1

:found
echo Starting Gatewayz Banking System (JavaFX)...
echo Using JavaFX from: %JAVAFX_PATH%
echo.

java --module-path "%JAVAFX_PATH%" ^
     --add-modules javafx.controls,javafx.fxml ^
     -jar "%~dp0GatewayzBanking-JavaFX.jar"

pause

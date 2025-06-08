@echo off
cd /d "%~dp0U Health Care"
if not exist bin mkdir bin
echo Compiling Java files...
javac -cp "lib/*" -d bin src\login\*.java src\main\*.java src\models\*.java src\panels\*.java
if errorlevel 1 (
    echo Compilation failed!
    pause
    exit /b 1
)
echo Running application...
java -cp "bin;lib/*" login.LoginFrame
pause 
@echo off
REM === Configurações do Java e JavaFX ===
set JAVA_HOME="G:\Program Files\jdk-21.0.8"
set JAVAFX_LIB="C:\javafx\lib"

REM === Compilar ===
echo Compilando projeto...
%JAVA_HOME%\bin\javac --module-path %JAVAFX_LIB% --add-modules javafx.controls,javafx.fxml Telas\Main.java

IF %ERRORLEVEL% NEQ 0 (
    echo Erro na compilacao!
    pause
    exit /b %ERRORLEVEL%
)

REM === Executar ===
echo Executando projeto...
%JAVA_HOME%\bin\java --module-path %JAVAFX_LIB% --add-modules javafx.controls,javafx.fxml Telas.Main

pause

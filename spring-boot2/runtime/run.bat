
@echo off
rem Use JAVA_HOME if it's set; otherwise, just use java

if "%JAVA_HOME%" == "" goto noJavaHome
if not exist "%JAVA_HOME%\bin\java.exe" goto noJavaHome
set JAVA_EXE="%JAVA_HOME%\bin\java.exe"
echo JAVA_HOME found: %JAVA_EXE%
echo.
goto startRadp

:noJavaHome
goto eof
echo The JAVA_HOME environment variable is not defined correctly.
echo Instead the PATH will be used to find the java executable.
echo.
set JAVA_EXE=java
goto startRadp

:startRadp

echo WARNING: If the Java version (below) is less than 1.8,
echo          this app will fail. Sorry in advance...
echo.
%JAVA_EXE% -version
TIMEOUT /T 30

set RADP_ROOT=%~dp0\
pushd "%RADP_ROOT%"

set RADP_CONF_DIR=%RADP_ROOT%\config
set RADP_CONF_FILE=%RADP_CONF_DIR%\poixls.properties
set TIMESTAMP=%DATE:~6,4%-%DATE:~3,2%-%DATE:~0,2%-%TIME:~0,2%%TIME:~3,2%%TIME:~6,2%

set JAVA_ARGS=-DconfigFile="%RADP_CONF_FILE%" -Dtimestamp="%TIMESTAMP%"
set PROG_ARGS=configDir=%RADP_CONF_DIR%

%JAVA_EXE% %JAVA_ARGS% -jar lib\poixls.jar %PROG_ARGS%

:eof

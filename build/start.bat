@echo off 
::  windows  ANSI

title ftp
::@mode con lines=18 cols=40

color 2f


echo     [get] get
echo     [put] put
echo     [ q ] quit
echo -----------------------------


:tag
set txt=
set /p txt="get or put ?:"
echo.
if "%txt%" == "q"  exit
if "%txt%" == "-q" exit
java -jar "%~dp0lib\ftp1.0.jar" %txt%
goto tag

pause

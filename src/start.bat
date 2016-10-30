@echo off 
::  windows  ANSI

title ftp
::@mode con lines=18 cols=40

color 2f


echo     【get】: 下载
echo     【put】: 上传
echo     【  q】: 退出
echo -----------------------------


:tag
set txt=
set /p txt="参数:"
echo.
if "%txt%" == "q"  exit
if "%txt%" == "-q" exit
java -jar "%~dp0lib\ftp1.0.jar" %txt%
goto tag

pause

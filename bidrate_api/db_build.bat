@echo off
setlocal

set MYSQL_PATH=C:\xampp\mysql\bin
set MYSQL_ADMIN=%MYSQL_PATH%\mysqladmin.exe
set MYSQL=%MYSQL_PATH%\mysql.exe

set DB=visian
set DBUSER=visian
set DBPWD=zuzuga

%MYSQL_ADMIN% -u root -p drop %DB%
%MYSQL_ADMIN% -u root -p create %DB%

set G0=flush privileges;drop user '%DBUSER%'@'localhost';
set G1=CREATE USER '%DBUSER%'@'localhost' IDENTIFIED BY '%DBPWD%';
set G2=CREATE USER '%DBUSER%'@'%' IDENTIFIED BY '%DBPWD%';
set G3=GRANT ALL ON %DB%.* TO '%DBUSER%'@'localhost';
set G4=GRANT ALL ON %DB%.* TO '%DBUSER%'@'%';
set SQL=%G0%%G1%%G2%%G3%%G4%

echo %SQL% | %MYSQL% -u root -p %DB%

pause

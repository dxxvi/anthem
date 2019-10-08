@echo off

FOR /L %%i IN (1,1,300) DO (
  C:\jdk-13\bin\java.exe -Dhost=localhost -jar target\screen-capture-1.2.jar
  timeout 2
)
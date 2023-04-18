:: This script builds the rest server image or pulls
:: it from dockerhub. Note that the build process can be slow
:: compared to pulling from dockerhub.

@echo off
:loop
set /p input="Compile locally (Y - slow) or pull from dockerhub (n - fast) ? (Y/n) : "
if /i "%input%"=="Y" (
    docker build -t dvd_spring_server .
    :: ./mvnw spring-boot:build-image
) else if /i "%input%"=="n" (
    docker pull ttomtsis/dvd-spring-server:latest
) else (
    goto loop
)

:: Will not use buildpack, due to long inigial build times.
:: Buildpack image can be found at dockerhub
:: https://hub.docker.com/repository/docker/ttomtsis/dvd-spring-server/general

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

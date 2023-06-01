:: This script builds the rest server image or pulls
:: it from dockerhub. Note that the build process can be slow
:: compared to pulling from dockerhub.

@echo off
:loop
echo (1) - Build image locally using dockerfile - very slow
echo (2) - Pull image from dockerhub ( 170 MB ) - fast
echo (3) - Pull native-image from dockerhub ( 60 MB ) - faster
set /p input="Enter your choice ( 1 / 2 / 3 ): "
if /i "%input%"=="1" (
    docker build -t dvd_spring_server .
) else if /i "%input%"=="2" (
    docker pull ttomtsis/dvd-spring-server:dockerfile
    doker tag ttomtsis/dvd-spring-server:dockerfile dvd_spring_server:latest
) else if /i "%input%"=="3" (
    docker pull ttomtsis/dvd-spring-server:native-image
    docker tag ttomtsis/dvd-spring-server:native-image dvd_spring_server:latest
) else (
    goto loop
)

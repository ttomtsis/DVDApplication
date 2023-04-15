:: Will not user buildpack, due to long build times.
:: Buildpack image can be found at dockerhub
:: https://hub.docker.com/repository/docker/ttomtsis/dvd-spring-server/general
:: ./mvnw spring-boot:build-image

./mvnw clean install

:: Build Rest Server image
docker build -t dvd_spring_server .
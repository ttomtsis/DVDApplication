:: This script creates the resources necessary for the containers
:: to function

:: # Create network and volume #
docker network create dvd_network
docker volume create mydb_volume

:: # Run images #
:: Mount volume at MySQL image and connect it at network. Run it as a daemon
:: Volume is mounted at /var/lib/mysql since that is the directory where MySQL stores databases
docker run -d --name dvd_mysql --env-file ../docker-env.env --network dvd_network -v mydb_volume:/var/lib/mysql mysql/mysql-server:8.0

:: Connect spring server to the same network and publish port 8080 to the host
docker run --name dvd_spring_server --env-file ../docker-env.env --network dvd_network --publish 8080:8080 dvd_spring_server

:: NOTE: -rm flag not added intentionally, in case user does not want to immediately delete the containers
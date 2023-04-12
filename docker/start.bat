:: # Create network and volume #

docker network create dvd_network
docker volume create mydb_volume

:: # Build images #

docker build -t dvd_mysql_server ./MySQLDocker
docker build -t dvd_spring_server ./SpringServer

:: # Run images #

:: Mount volume at MySQL image and connect it at network. Run it as a daemon
:: Volume is mounted at /var/lib/mysql since that is the directory where MySQL stores databases
docker run -d --name dvd_mysql --network dvd_network -v mydb_volume:/var/lib/mysql dvd_mysql_server

:: Connect spring server to the same network and publish port 8080 to the host
docker run --name dvd_spring_server --network dvd_network --publish 8080:8080 dvd_spring_server

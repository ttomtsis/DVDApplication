:: Disconnect running containers from network
docker network disconnect dvd_network dvd_spring_server
docker network disconnect dvd_network dvd_mysql

:: Remove network
docker network rm dvd_network

:: Stop running containers
docker stop dvd_spring_server
docker stop dvd_mysql

:: Delete containers
docker rm dvd_spring_server
docker rm dvd_mysql

:: Delete volume
docker volume rm mydb_volume

:: Delete images
docker rmi dvd_spring_server
docker rmi dvd_mysql_server
:: Stops the compose deployment and deletes the volume
:: AND ALSO DELETES THE SERVER IMAGE
docker compose -f ../docker-compose.yml stop
docker compose -f ../docker-compose.yml down

docker volume rm docker_mydb_volume

docker rmi dvd_spring_server

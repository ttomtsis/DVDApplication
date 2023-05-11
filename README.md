# DVDApplication

This is part of a university course from University of the Aegean,
aiming to provide students with practical experience on cloud technologies

**Concept**: Java Spring Boot application that provides a simple RESTful API service for a DVD store. The API allows users to perform CRUD operations on a MySQL database containing DVD records. The application includes user authentication using Spring Security, and follows HATEOAS principles for API design. It also includes Docker for containerization and can be run on a minikube created Kubernetes cluster.

Associated DockerHub container repository: 
https://hub.docker.com/repository/docker/ttomtsis/dvd-spring-server/general

# Features
* Role-based authorization with Spring Security
* HATEOAS
* Docker support
* Kubernetes support
* MySQL and InnoDB Cluster
* GraalVM Native Image

# Technologies
* Java 17
* Spring Boot 3.1.0
* Spring Security
* MySQL 8.0
* HATEOAS
* Docker
* Kubernetes
* GraalVM 22 ( for native image creation )

# Getting Started
To get started with this project, you can choose either to run it locally your host machine, on docker or in a single node Kubernetes cluster using minikube. 
The spring boot app consists of two profiles, the 'default' profile uses an H2 database whereas the 'containerized' profile uses a MySQL Database by default.
**The inscructions provided below concern only Windows**.

## Database configuration
If you want to use a normal database you must set the environment variable **SPRING_PROFILES_ACTIVE** equal to 'containerized'
Also you must configure the application to use your credentials when connecting to the specified database by setting the following environment variables:

 - **DB_USERNAME** : The username that you use when you connect to the database
 - **DB_PASSWORD** : The password that you use when you connect to the database
 - **DATASOUCE_URL** : The url to your database. You can use any database that is compatible with Spring Boot like PostgreSQL or MySQL etc, however note that
 this application was developed and tested while using MySQL 8.0. Also take care to use the correct driver in the provided url.

## Locally
You will need to have the following installed on your machine:

* Java 17+
* Maven 3.9.1+, or you can use the provided maven wrapper
* A database compatible with Spring Boot, either installed or running in a container.

To build and run the project, follow these steps:

* Clone the repository to your local machine: `git clone https://github.com/ttomtsis/DVDApplication`
* Navigate to the project directory
* Build the project: `mvn clean install`
* Run the project: `mvn spring-boot:run`

## Docker
To run the project on Docker, make sure you have Docker installed on your machine.
The provided scripts use a MySQL Database by default

### Option 1: Manual Docker Build
* Clone the repository to your local machine `git clone https://github.com/ttomtsis/DVDApplication`
* Navigate to the project directory
* Run the `setup.bat` file.This will create a Docker image for the application: `./setup.bat`
* Navigate to the docker-scripts directory
* Start the containers by running the `start.bat` file: `./start.bat`
* Stop the containers and remove them along with the images,networks and volumes by running the `stop.bat` file: `./stop.bat`

### Option 2: Docker Compose
* Clone the repository to your local machine `git clone https://github.com/ttomtsis/DVDApplication`
* Navigate to the docker-scripts directory, located inside the project directory
* Start the containers by running the compose-start.bat file: `./compose-start.bat`
* Delete the containers, networks and associated vlumes by running the compose-stop.bat file: `./compose-stop.bat`

## Kubernetes
To run the project on Minikube, make sure you have python 3, Minikube and kubectl installed and properly configured on your machine.
* Clone the repository to your local machine: `git clone https://github.com/ttomtsis/DVDApplication`
* Navigate to the yaml folder in the project directory
* Run the `setup.bat` file. This script initializes a minikube cluster with a REST server, a MySQL Router and an InnoDB cluster with 3 MySQL databases 
* Get the minikube IP address. Keep this window open.
* Access the application's endpoints at the given IP address
* If you want to remove all traces of the application run the `delete.bat` script
* Experimental secret encryption in the cluster through `enable-encryption.bat` script

# Endpoints

* GET `/api/dvds` - Retrieves a list of all DVDs.
* GET `/api/dvds?name=dvdName` - Retrieves a list of DVDs that match the specified title.
* GET `/api/dvds/{dvdID}` - Retrieves details about a specific DVD.
* POST `/api/dvds` - Adds a new DVD to the database.
* PUT `/api/dvds/{dvdID}` - Updates the quantity and genre of an existing DVD.
* DELETE `/api/dvds/{dvdID}` - Deletes a DVD from the database.


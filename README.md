# DVDApplication

This is part of a university course from University of the Aegean,
aiming to provide students with practical experience on cloud technologies

**Concept**: 

Java Spring Boot application that provides a simple RESTful API service for a DVD store. The API allows users to perform CRUD operations on a MySQL database containing DVD records. The application includes user authentication using Spring Security, and follows HATEOAS principles for API design. It also includes Docker for containerization and can be run on a minikube created Kubernetes cluster.

Associated DockerHub container repository: 
https://hub.docker.com/repository/docker/ttomtsis/dvd-spring-server/general

# Features
* Role-based authorization, Basic Authentication
* HATEOAS
* Docker support
* Kubernetes support
* MySQL and MySQL Operator with InnoDB cluster
* GraalVM Native Image and executable
* TLS v1.3 support with dummy certificates

# Technology stack
* Java 17
* Spring Boot 3.0.5
* Spring Security 6
* MySQL 8.0
* HATEOAS 3
* Docker
* Kubernetes
* GraalVM 22

# Getting Started
To get started with this project, you can choose either to run it locally on your host machine, on docker or in a single node Kubernetes cluster using minikube. 
The spring boot app consists of **two profiles** similar to each other:

1) The '**default profile**' should be used for local development and testing. Used an H2 database unless configured otherwise
and lacks more advanced features spring actuator.

2) The '**containerized profile**' should be used when the application is containerised in docker, deployed in Kubernetes
**or if running as a native image or executable**. It is similar to the default profile but contains some extra properties ( i.e. graceful shutdown ).
Set the active profile in windows powershell: `$env:SPRING_PROFILES_ACTIVE="containerized"`

A dummy root CA certificate is provided in the `resources/tls` directory. You can install this in your system.
There is also a server certificate provided, which has been signed by the dummy CA. Feel free to replace those certificates as needed.
**The scripts used below were created for the Windows OS**.

## Database configuration
To use a normal database you must configure the application to use your credentials when connecting to the
specified database by setting the following environment variables:

 - **DB_USERNAME** : The username that you use when you connect to the database
 - **DB_PASSWORD** : The password that you use when you connect to the database
 - **DATASOUCE_URL** : The url pointing to the location of your database, either local or remote.

## TLS configuration
You can use the provided certificates or you can override the following properties, via environment variables, to implement your own
configuration

- **server.ssl.key-store** : The location of the keyfile
- **server.ssl.key-store-password** : The password required ( if required ) in order to access the key file
- **server.ssl.key-store-password** : The type of the keyfile
- **server.ssl.keyAlias** : The alias associated with the key file

## Locally

### Build with maven 
You will need to have the following installed on your machine:

* Java 17+
* Maven 3.9.1+, or you can use the provided maven wrapper
* MySQL Server installed or running in a container.

To build and run the project, follow these steps:

* Clone the repository to your local machine: `git clone https://github.com/ttomtsis/DVDApplication`
* Navigate to the project directory
* Build the project: `mvn clean install`
* Run the project: `mvn spring-boot:run`

### Native executable
You can also download the latest native executable
( https://github.com/ttomtsis/DVDApplication/releases ), configure it as described above
and run it without any prerequisites

## Docker
To run the project on Docker, make sure you have Docker installed on your machine.

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
* Delete the containers, networks and associated volumes by running the compose-stop.bat file: `./compose-stop.bat`

### Option 3: Dockerhub
* Check out the associated dockerhub repository: https://hub.docker.com/repository/docker/ttomtsis/dvd-spring-server/general
* Pull the desired image and use the `start.bat` script to run it `docker pull ttomtsis/dvd-spring-server:latest`

**NOTE**: If you pull a tag other than 'latest' you will need to tag the pulled image to 'dvd-spring-server:latest'
in order for the script to work properly

## Kubernetes
To run the project on Minikube, make sure you have python 3, minikube and kubectl installed and properly configured on your machine.
You will also need to install the MySQL Operator ( this process will be automated in the future ).
For info on how to install the MySQL Operator refer to its official GitHub repository:
https://github.com/mysql/mysql-operator

* Clone the repository to your local machine: `git clone https://github.com/ttomtsis/DVDApplication`
* Navigate to the yaml folder in the project directory
* Run the `setup.py` file. This script initializes a minikube cluster with a REST server, and an InnoDB Cluster with 3 databases
* Get the minikube IP address. Keep this window open.
* Access the application's endpoints at the given IP address
* If you want to remove all traces of the application run the `delete.bat` script

# Endpoints

**DVD CRUD Operations**
* GET `/api/dvds` - Retrieves a list of all DVDs.
* GET `/api/dvds?name=dvdName` - Retrieves a list of DVDs that match the specified title.
* GET `/api/dvds/{dvdID}` - Retrieves details about a specific DVD.
* POST `/api/dvds` - Adds a new DVD to the database.
* PUT `/api/dvds/{dvdID}` - Updates the quantity and genre of an existing DVD.
* DELETE `/api/dvds/{dvdID}` - Deletes a DVD from the database.

**Actuator**

**NOTE:** If the server is running as a native image or executable, only the `/server/health` and `/server/logs`
endpoints are available.
* GET `/server` - Provides a list of all actuator endpoints
* GET `/server/info` - Returns basic information about the application
* GET `/server/mappings` - Provides an exhaustive list and description of all the endpoints
* GET `/server/logs` - Returns the contents of the servers logfile
* GET `/server/beans` - Returns an exhaustive list and description of the application's beans
* GET `/server/health` - Return the server's running state
* GET `/server/health/{*path}` - Provides information for the specified custom health metric
* GET `/server/env` - Provides a list of all spring configured properties
* GET `/server/env/{toMatch}` - Returns information about the specified property
* GET `/server/loggers` - Provides a list of all available loggers
* GET `/server/loggers/{name}` - Returns the logging level of a specific logger
* POST `/server/loggers/{name}` - Change the `configuredLevel` of the specified logger
* GET `/server/metrics` - Provides a list of the supported application metrics
* GET `/server/metrics/{MetricName}` - Returns the value of the specified metric

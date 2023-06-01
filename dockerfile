# Using the openjdk only to allow the maven wrapper
# to create the jarfile that will use in the second stage
# NOTE: Chose bellsoft baseimages since the Spring Team also
# recommends them
FROM bellsoft/liberica-openjdk-alpine as build

LABEL MAINTAINER="BellSoft, <info@bell-sw.com>"
LABEL AUTHOR="Thomas Tomtsis, <icsd15201@icsd.aegean.gr>"

RUN mkdir /opt/DVDApplication
COPY . /opt/DVDApplication
WORKDIR /opt/DVDApplication

# Using the maven wrapper with the bellsoft openjdk
# due to the smaller size of the bellsoft openjdk compared to a maven baseimage
# Note that compiling the jar can take some time, so the setup script
# also offers the option to pull the image from dockerhub
RUN ./mvnw clean package

# Now using only the jre in order to trim down the resulting image size
FROM bellsoft/liberica-runtime-container:jre-slim

RUN mkdir /opt/DVDApplication
COPY --from=build /opt/DVDApplication/target/DVDApplication-0.0.1-SNAPSHOT.jar /opt/DVDApplication
WORKDIR /opt/DVDApplication

ENTRYPOINT ["java", "-jar", "DVDApplication-0.0.1-SNAPSHOT.jar"]
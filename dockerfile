FROM bellsoft/liberica-openjdk-alpine as build

LABEL MAINTAINER="BellSoft, <info@bell-sw.com>"
LABEL AUTHOR="Thomas Tomtsis, <icsd15201@icsd.aegean.gr>"

RUN mkdir /opt/DVDApplication
COPY . /opt/DVDApplication
WORKDIR /opt/DVDApplication

RUN ./mvnw clean install

FROM bellsoft/liberica-runtime-container:jre-slim

RUN mkdir /opt/DVDApplication
COPY --from=build /opt/DVDApplication/target/DVDApplication-0.0.1-SNAPSHOT.jar /opt/DVDApplication
WORKDIR /opt/DVDApplication

ENTRYPOINT ["java", "-jar", "DVDApplication-0.0.1-SNAPSHOT.jar"]
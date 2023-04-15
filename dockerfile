FROM ibm-semeru-runtimes:open-17.0.6_10-jdk-focal
LABEL MAINTAINER="IBM SEMERU RUNTIMES"
LABEL AUTHOR="Thomas Tomtsis, icsd15201@icsd.aegean.gr"
RUN mkdir /opt/DVDApplication
COPY ./target/DVDApplication-0.0.1-SNAPSHOT.jar /opt/DVDApplication

CMD ["java", "-jar", "/opt/DVDApplication/DVDApplication-0.0.1-SNAPSHOT.jar"]
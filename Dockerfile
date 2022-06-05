FROM openjdk:17.0.2-jdk

ARG URL
ARG USERNAME
ARG PASSWORD

COPY ./target/carwash-0.0.1-SNAPSHOT.jar /usr/app/

#EXPOSE 8080

#CMD sleep 15m;
ENTRYPOINT [ "java", "-jar", "/usr/app/carwash-0.0.1-SNAPSHOT.jar"]

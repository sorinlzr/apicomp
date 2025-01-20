FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs/apicomp-0.0.1-SNAPSHOT.jar /app/apicomp.jar

EXPOSE 8080
EXPOSE 9090

ENTRYPOINT ["java", "-jar", "/app/apicomp.jar"]
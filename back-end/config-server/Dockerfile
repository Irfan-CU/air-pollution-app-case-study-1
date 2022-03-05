FROM openjdk:11-jdk
ADD  target/*.jar config-server-0.0.1-SNAPSHOT.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "config-server-0.0.1-SNAPSHOT.jar"]

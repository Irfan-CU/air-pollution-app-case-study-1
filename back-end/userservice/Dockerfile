FROM openjdk:11-jdk
ADD  target/*.jar userservice-0.0.1-SNAPSHOT.jar
EXPOSE 9099
ENTRYPOINT ["java", "-jar", "userservice-0.0.1-SNAPSHOT.jar"]

FROM openjdk:8
ADD target/spring-boot-first-web-application-0.0.1-SNAPSHOT.jar todo.jar
ENTRYPOINT ["java","-jar","todo.jar"]
EXPOSE 8081


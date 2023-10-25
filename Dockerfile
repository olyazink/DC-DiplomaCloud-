FROM openjdk:17-jdk-alpine

EXPOSE 8088

ADD /target/DiplomCloud-0.0.1-SNAPSHOT.jar app.jar

CMD ["java" , "-jar", "app.jar"]
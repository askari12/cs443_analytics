FROM openjdk:8
EXPOSE 8080
ADD target/analytics.jar analytics.jar
ENTRYPOINT ["java" , "-jar" , "/analytics.jar"]

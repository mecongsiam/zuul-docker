FROM java:8u111-jdk-alpine

ADD gateway.jar /app/gateway.jar

WORKDIR /app

EXPOSE 8090

ENTRYPOINT ["java", "-Dspring.profiles.active=container", "-jar", "gateway.jar"]

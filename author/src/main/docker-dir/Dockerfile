FROM java:8u111-jdk-alpine

ADD author.jar /app/author.jar

WORKDIR /app

EXPOSE 8070

ENTRYPOINT ["java", "-Dspring.profiles.active=container", "-jar", "author.jar"]

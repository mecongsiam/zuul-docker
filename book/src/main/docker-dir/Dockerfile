FROM java:8u111-jdk-alpine

ADD book.jar /app/book.jar

WORKDIR /app

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=container", "-jar", "book.jar"]

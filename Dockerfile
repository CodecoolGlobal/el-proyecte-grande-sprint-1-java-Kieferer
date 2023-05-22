FROM openjdk:17-jdk
WORKDIR /app
COPY target/budapestgo-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java", "-jar", "budapestgo-0.0.1-SNAPSHOT.jar"]
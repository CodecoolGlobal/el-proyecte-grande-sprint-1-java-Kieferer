FROM openjdk:17-jdk
WORKDIR /app
COPY target/budapestgo*.jar budapestgo.jar
EXPOSE 8080
CMD ["java", "-jar", "budapestgo.jar"]

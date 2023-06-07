FROM maven:3.8.3-openjdk-17-slim AS build
RUN mkdir /project
COPY . /project
WORKDIR /project
RUN mvn clean install

FROM openjdk:17-jdk-slim
RUN mkdir /app
COPY --from=build /project/target/robodog-*.jar robodog.jar
WORKDIR /app
COPY target/budapestgo*.jar budapestgo.jar
EXPOSE 8080
CMD ["java", "-jar", "budapestgo.jar"]

FROM maven:3.8.3-openjdk-17-slim AS build
RUN mkdir /project
COPY . /project
WORKDIR /project
RUN mvn clean install

FROM openjdk:17-jdk-slim
RUN mkdir /app
COPY --from=build /project/target/budapestgo*.jar budapestgo.jar
WORKDIR /app
EXPOSE 8080
CMD ["java", "-jar", "budapestgo.jar"]

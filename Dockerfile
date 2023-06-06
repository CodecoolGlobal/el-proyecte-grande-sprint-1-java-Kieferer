FROM maven:3.8.3-openjdk-17-slim AS build
RUN mkdir /build
COPY . /build
WORKDIR /build
RUN mvn clean install -DskipTests

FROM openjdk:17-jdk
WORKDIR /app
COPY --from=build /build/target/budapestgo*.jar budapestgo.jar
WORKDIR /app
EXPOSE 8080
CMD ["java", "-jar", "budapestgo.jar"]

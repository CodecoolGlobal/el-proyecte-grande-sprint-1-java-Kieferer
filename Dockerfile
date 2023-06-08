FROM maven:3.8.3-openjdk-17-slim AS build
RUN mkdir /project
COPY . /project
WORKDIR /project
RUN mvn clean install

FROM postgres:latest AS postgres
ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD postgres
ENV POSTGRES_DB budapestgo

FROM openjdk:17-jdk-slim
COPY --from=build /project/target/budapestgo*.jar budapestgo.jar
COPY --from=postgres /usr/local/bin/docker-entrypoint.sh /usr/local/bin/docker-entrypoint.sh
EXPOSE 8080
CMD ["sh", "-c", "/usr/local/bin/docker-entrypoint.sh postgres & java -jar budapestgo.jar"]

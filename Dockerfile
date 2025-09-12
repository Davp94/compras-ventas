FROM maven:3.9.11-amazoncorretto-21-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM amazoncorretto:21-alpine3.21
WORKDIR /app
COPY --from=build /app/target/compras-ventas-*.jar app.jar

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=dev
ENV FILE_PATH=/home/david/test

ENTRYPOINT [ "sh", "-c", "java -jar app.jar" ]
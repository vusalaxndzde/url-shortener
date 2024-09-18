#
# Build stage
#
FROM maven:3.9.9-eclipse-temurin-17-alpine AS build
COPY . .
RUN mvn clean install

#
# Package stage
#
FROM eclipse-temurin:17-jdk-alpine
COPY --from=build /target/url-shortener-0.0.1.jar url-shortener-0.0.1.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "url-shortener-0.0.1.jar", "--spring.profiles.active=prod"]
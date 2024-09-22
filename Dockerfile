#
# Build stage
#
FROM maven:3.8.3-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml /app
ARG MAIN_CLASS=/src/main/java/com/vusalaxndzde/url_shortener/UrlShortenerApplication.java
COPY ${MAIN_CLASS} /app${MAIN_CLASS}
RUN mvn clean package -DskipTests
COPY . /app
RUN mvn clean package

#
# Package stage
#
FROM openjdk:17-jdk-slim
COPY --from=build /app/target/*.jar url-shortener.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "url-shortener.jar", "--spring.profiles.active=prod"]
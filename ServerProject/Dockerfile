FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app
COPY ServerProject/pom.xml ./
COPY ServerProject/src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

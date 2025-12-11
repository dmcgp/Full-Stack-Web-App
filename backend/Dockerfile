# Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn -q -DskipTests package

# Runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/taskmanager-0.0.1-SNAPSHOT.jar /app/taskmanager.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/taskmanager.jar"]

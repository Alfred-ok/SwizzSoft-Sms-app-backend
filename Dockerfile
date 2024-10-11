#Part 1: compile and package application
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

#Part 1: run application
#container
FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/SwizzSoft-Sms-app-0.0.1-SNAPSHOT.jar SwizzSoft-Sms-app.jar

#Expose the port on which your Spring Boot app will run (usually 8080)
EXPOSE 8080

#Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "SwizzSoft-Sms-app.jar"]


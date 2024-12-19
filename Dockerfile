#FROM maven:3.8.3-openjdk-17 AS builder
#
#COPY ./src/ /root/src
#COPY ./pom.xml /root/
#WORKDIR /root
#RUN mvn clean package -DskipTests
#
#FROM openjdk:17
#
#ENV TZ=UTC
#
#
#COPY --from=builder /root/target/chat-0.0.1-SNAPSHOT.jar /app/app.jar
#
#EXPOSE 8083:8083
#
#ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# Use an offical Maven image to bulid the Spring Boot app
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and install dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests

# Use an official OpenJDK image to run the application
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the bulid JAR file from the build stage
COPY --from=build /app/target/chat-0.0.1-SNAPSHOT.jar .

# Expose port 8080
EXPOSE 8083

# Specify the command to run the application
ENTRYPOINT ["java", "-jar", "/app/chat-0.0.1-SNAPSHOT.jar"]
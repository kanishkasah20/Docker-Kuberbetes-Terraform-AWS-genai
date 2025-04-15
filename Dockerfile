# FROM openjdk:23
# EXPOSE 8080
# # add jar of this springboot docker
# ADD target/spring-boot-docker.war spring-boot-docker.war
# # ADD target/demo.jar demo.jar
# # entrypoint -> specify the command to run the jar
# ENTRYPOINT ["java","-jar","/spring-boot-docker.war"]


# Stage 1: Build the application
# FROM openjdk:21-jdk AS build
# WORKDIR /app 
# COPY . .
# RUN ./gradlew build -x test --no-daemon

# # Stage 2: Create the runtime image
# # FROM openjdk:21-jre-slim
# # WORKDIR /app
# # COPY --from=build /app/build/libs/demo-0.0.1-SNAPSHOT.jar app.jar
# EXPOSE 8080
## command to execute my jar file
# ENTRYPOINT [ "java","-jar","app.jar" ]

FROM openjdk:23
WORKDIR /app
COPY target/demo-0.0.1-SNAPSHOT.war demo.jar
EXPOSE 8080
ENTRYPOINT [ "java","-jar","demo.jar" ]
FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} APP.JAR
# Make port 3000 available to the world outside this container
EXPOSE 3000
ENTRYPOINT ["java","-jar","/app.jar"]
# Build a JAR File
FROM maven:3.8.2-jdk-8-slim AS stage1
WORKDIR /home/app
COPY . /home/app/
RUN mvn -f /home/app/pom.xml clean package -DskipTests

# Create an Imageeee
FROM openjdk:8-jdk-alpine
EXPOSE 8083
EXPOSE 8080
COPY --from=stage1 /home/app/target/*.jar try.jar
ENTRYPOINT ["sh", "-c", "java -jar /try.jar"]

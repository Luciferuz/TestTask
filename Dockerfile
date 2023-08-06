FROM maven:3.8.3-openjdk-17 as builder
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package -Dmaven.test.skip=true

FROM openjdk:18.0-jdk-slim
RUN apt-get update && apt-get install -y netcat
COPY --from=builder /usr/src/app/target/TestTask-0.0.1-SNAPSHOT.jar /usr/app/TestTask-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/usr/app/TestTask-0.0.1-SNAPSHOT.jar"]
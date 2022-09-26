FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD

COPY pom.xml /build/

COPY src /build/src/

WORKDIR /build/

RUN mvn clean package
#Build MAVEN

FROM openjdk:8-jre-alpine

WORKDIR /app

COPY --from=MAVEN_BUILD /build/target/MucaTour-0.0.1-SNAPSHOT.jar /app/mt-api.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "mt-api.jar"]
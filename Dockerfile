FROM maven:3.6.0-jdk-11 AS build
COPY . ./
RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:11
COPY --from=build target/*.jar ./app.jar
COPY public/images public/images
EXPOSE 8081
ENTRYPOINT ["java","-jar","./app.jar"]
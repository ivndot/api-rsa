# GET MAVEN
FROM maven:3-openjdk-18 AS build
# COPY PROJECT TO MAVEN
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
# BUILD THE PROJECT
RUN mvn -f /usr/src/app/pom.xml clean package

# GET TOMCAT
FROM tomcat:8-jdk8-corretto
# COPY WAR
COPY --from=build /usr/src/app/target/api-rsa.war /usr/local/tomcat/webapps.dist/
# RENAME THE DIRECTORIES
RUN mv /usr/local/tomcat/webapps /usr/local/tomcat/webapps2
RUN mv /usr/local/tomcat/webapps.dist /usr/local/tomcat/webapps
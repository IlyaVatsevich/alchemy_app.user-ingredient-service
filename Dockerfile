FROM openjdk:17-oracle
MAINTAINER IV
COPY target/user_ingredient_service-1.0.0.jar user_ingredient_service-1.0.0.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/user_ingredient_service-1.0.0.jar"]
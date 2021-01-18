FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} inventoryapp.jar
ENTRYPOINT ["java","-jar","/inventoryapp.jar"]
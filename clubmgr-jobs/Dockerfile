FROM openjdk:21
ARG JAR_FILE=target/clubmgr-jobs-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

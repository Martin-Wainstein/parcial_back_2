FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=eureka-ms-0.0.1-SNAPSHOT.jar
COPY target/${JAR_FILE} app.jar
COPY wait-for-it.sh .
ENTRYPOINT ["./wait-for-it.sh", "-t", "30","config-server:8888", "--", "java","-jar","app.jar"]
EXPOSE 8761
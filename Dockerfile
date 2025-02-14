FROM amazoncorretto:21-alpine
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 3000
ENTRYPOINT ["java","-jar","/app/app.jar"]
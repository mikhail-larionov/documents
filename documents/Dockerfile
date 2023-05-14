FROM openjdk:11
ADD /build/libs/documents-0.0.1-SNAPSHOT.jar documents.jar
ENV kafka.bootstrapAddress=localhost:29092
#ENV kafka.bootstrap.servers=kafka:29092
EXPOSE 8080
ENTRYPOINT ["java","-jar","documents.jar"]

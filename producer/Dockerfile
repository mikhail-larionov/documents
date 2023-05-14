FROM openjdk:11
ADD /build/libs/producer-0.0.1-SNAPSHOT.jar producer.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","producer.jar"]
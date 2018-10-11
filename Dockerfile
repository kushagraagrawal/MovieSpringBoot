FROM openjdk:10
ADD ./target/movie-0.0.1-SNAPSHOT.jar  /usr/src/movie-0.0.1-SNAPSHOT.jar
EXPOSE 8080
WORKDIR /usr/src
ENTRYPOINT ["java","-jar", "movie-0.0.1-SNAPSHOT.jar"]


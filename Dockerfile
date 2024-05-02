FROM openjdk:23-jdk


WORKDIR /

COPY target/uberjar/hello-http-server-0.1.0-SNAPSHOT-standalone.jar hello-http-server.jar
COPY resources resources
EXPOSE 8000

CMD java -jar hello-http-server.jar
FROM openjdk:11

RUN apt-get update && apt-get install -y ncat
RUN groupadd -r web && useradd --no-log-init -r -g web web
RUN mkdir web/ && chown web:web web/
WORKDIR web

# Generate jar with `mvn package spring-boot:repackage`
COPY target/appsec-edu-1.0-SNAPSHOT.jar ./appsec.jar
USER web
CMD ["java", "-jar", "appsec.jar"]

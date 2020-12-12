FROM maven:3.6.3-openjdk-15 AS build

WORKDIR /build
COPY pom.xml /build/pom.xml
COPY src /build/src

RUN mvn package -DskipTests

FROM openjdk:15-slim

RUN apt-get update -yq && \
    apt-get install -yq tini && \
    rm -rf /var/lib/apt/lists/*
ENTRYPOINT ["tini", "--"]

WORKDIR /app
COPY --from=build /build/target/dare-0.0.2.jar /app/

RUN chown -R 1000:1000 /app
USER 1000

CMD ["java", "-jar", "dare-0.0.2.jar"]

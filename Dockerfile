FROM maven:3.8.4-openjdk-8 AS artifact-build
WORKDIR /oauth-generator/build
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src src
RUN mvn clean install

FROM openjdk:8u322-jre
ARG TARGET_PATH=/oauth-generator/build/target
COPY --from=artifact-build ${TARGET_PATH}/*.jar app.jar
ENV CONSUMER_KEY="defaultConsumerKey"
ENV CONSUMER_SECRET="defaultConsumerSecret"
ENV HTTP_METHOD="GET"
ENV URL="default.url"
ENTRYPOINT ["sh", "-c", "java -jar -DconsumerKey=${CONSUMER_KEY} -DconsumerSecret=${CONSUMER_SECRET} -DhttpMethod=${HTTP_METHOD} -Durl=${URL} /app.jar"]

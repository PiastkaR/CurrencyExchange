FROM openjdk:21-slim
VOLUME /tmp
COPY build/libs/CurrencyExchange.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
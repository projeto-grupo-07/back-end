FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Pega o .jar que o GitHub Actions gerou na pasta target
COPY target/*.jar app.jar

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=dev \
    SPRING_RABBITMQ_HOST=rabbitmq \
    SPRING_RABBITMQ_PORT=5672 \
    SPRING_RABBITMQ_USERNAME=admin \
    SPRING_RABBITMQ_PASSWORD=admin

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
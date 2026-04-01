FROM maven:3.9.5-eclipse-temurin-21 AS builder
WORKDIR /app

# Copia pom + fonte
COPY pom.xml .
COPY src ./src

# Evita re-baixar dependências desnecessariamente quando mudar apenas código:
RUN mvn -B -q -DskipTests dependency:go-offline

# Build jar (skip tests para acelerar; remova -DskipTests se quiser rodar testes no build)
RUN mvn -B -DskipTests package

# Stage 2: runtime
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copia jar gerado
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

# Variáveis de ambiente padrão podem ser sobrescritas pelo docker-compose / docker run
ENV SPRING_PROFILES_ACTIVE=dev \
    SPRING_RABBITMQ_HOST=localhost \
    SPRING_RABBITMQ_PORT=5672 \
    SPRING_RABBITMQ_USERNAME=user \
    SPRING_RABBITMQ_PASSWORD=password

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

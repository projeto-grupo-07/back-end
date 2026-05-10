# Build stage - igual ao dev
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage - otimizado para produção
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Adicionar usuário não-root por segurança
RUN useradd -m -u 1000 appuser

# Copiar o JAR compilado
COPY --from=builder /app/target/*.jar app.jar
RUN chown appuser:appuser app.jar

USER appuser

EXPOSE 8080

# Variáveis de ambiente para prod (devem ser passadas no runtime)
ENV SPRING_PROFILES_ACTIVE=prod \
    JAVA_OPTS="-Xmx512m -Xms256m"

HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
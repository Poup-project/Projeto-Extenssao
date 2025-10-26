# Estágio de build
FROM maven:3.9.8-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Estágio de produção
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Instala wait-for e dependências
RUN apk add --no-cache bash postgresql-client

# Copia o JAR do estágio de build
COPY --from=builder /app/target/*.jar app.jar

# Cria um usuário não-root para segurança
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

# Expõe a porta da aplicação
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Comando de execução
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
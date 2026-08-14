# Etapa de construcción
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
COPY . .
# Damos permisos al wrapper de Maven y construimos el .jar omitiendo las pruebas por ahora
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Copiamos solo el .jar generado en la etapa anterior
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
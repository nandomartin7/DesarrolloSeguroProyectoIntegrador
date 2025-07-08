# ─── Build ───────────────────────────────────────────────────────────────────
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

# Copiamos POM y código fuente
COPY pom.xml .
COPY src ./src

# Descarga dependencias y empaqueta
RUN mvn dependency:go-offline -B
RUN mvn package -DskipTests -B

# ─── Runtime ─────────────────────────────────────────────────────────────────
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]

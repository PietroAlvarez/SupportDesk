FROM node:26-alpine AS frontend-build
WORKDIR /app/frontend

COPY frontend/package.json frontend/package-lock.json ./
RUN npm ci

COPY frontend/ ./
RUN npm run build

FROM maven:3.9-eclipse-temurin-21 AS backend-build
WORKDIR /app/backend

COPY backend/pom.xml ./
RUN mvn -q -DskipTests dependency:go-offline

COPY backend/src ./src
COPY --from=frontend-build /app/frontend/dist/frontend/browser ./src/main/resources/static
RUN mvn -q -DskipTests package

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

RUN addgroup -S app && adduser -S app -G app
COPY --from=backend-build /app/backend/target/supportdesk-api-0.0.1-SNAPSHOT.jar app.jar

USER app
EXPOSE 10000
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]

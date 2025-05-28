FROM eclipse-temurin:17-jdk as builder

WORKDIR /app

# 1. Копируем только необходимые файлы для Maven
COPY .mvn/ .mvn
COPY mvnw .
COPY pom.xml .
RUN chmod +x mvnw  # Даем права на выполнение

# 2. Копируем исходники и собираем проект
COPY src/ src
RUN ./mvnw clean package

# 3. Финальный образ
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/target/Mobileshop-*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]

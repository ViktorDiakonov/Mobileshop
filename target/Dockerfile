# Используем официальный Java 17 JDK образ
FROM eclipse-temurin:17-jdk

# Копируем JAR-файл в контейнер
COPY Mobileshop-0.0.1-SNAPSHOT.jar app.jar

# Указываем порт, если Render его подаёт через переменную
ENV PORT=8080
EXPOSE ${PORT}

# Запускаем приложение
CMD ["java", "-jar", "app.jar"]

# Используем официальный Java образ
FROM eclipse-temurin:17-jdk

# Копируем .jar в контейнер
COPY MySpringBootApp-0.0.1-SNAPSHOT.jar app.jar

# Указываем команду запуска
CMD ["java", "-jar", "app.jar"]

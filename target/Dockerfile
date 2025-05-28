FROM eclipse-temurin:17-jdk as builder
WORKDIR /app
COPY . .
RUN chmod +x ./mvnw
RUN ./mvnw clean package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/target/Mobileshop-*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]


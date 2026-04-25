FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY . .

# ✅ ADD THIS LINE (fix permission)
RUN chmod +x mvnw

# Build the app
RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["sh", "-c", "java -jar target/*.jar --server.port=${PORT}"]
# Use Java 17 (Spring Boot 2.7+ works well with this)
FROM eclipse-temurin:17-jdk-alpine

# Create app directory
WORKDIR /app

# Copy project files
COPY . .

# Build the app
RUN ./mvnw clean package -DskipTests

# Expose port (Render uses PORT env)
EXPOSE 8080

# Run the jar
CMD ["sh", "-c", "java -jar target/*.jar --server.port=${PORT}"]
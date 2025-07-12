FROM openjdk:21-jdk-slim

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivos de Gradle
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Dar permisos de ejecución al gradlew
RUN chmod +x ./gradlew

# Copiar código fuente
COPY src src

# Construir la aplicación
RUN ./gradlew build -x test

# Exponer puerto
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "build/libs/parcial3-0.0.1-SNAPSHOT.jar"]
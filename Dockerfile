# Используем официальный JDK образ
FROM openjdk:21-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем собранный jar-файл
COPY build/libs/app.jar app.jar

# Запуск приложения
ENTRYPOINT ["java", "-jar", "app.jar"]

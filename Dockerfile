# Используем официальный образ PostgreSQL
FROM postgres:latest

# Устанавливаем переменные среды
ENV POSTGRES_DB=profile_db
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=1

# Копируем SQL-скрипт для инициализации (если нужны дополнительные настройки)
#COPY init.sql /docker-entrypoint-initdb.d/

# (Опционально) Можно указать порт, если нужно изменить стандартный
EXPOSE 5430


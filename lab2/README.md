**Для запуску потрібно мати:**  
docker  
docker-compose

**Запуск:**  
1. Перейти в директорію з проектом:  
cd smtrpz/lab2

2. Запустити з командної строки або терміналу:   
docker-compose up --scale movie-service=2

**URL:**  
Eureka Server: http://localhost:8761  
Service 1: http://localhost:8081  
Service 2: http://localhost:8082  
Api Gateway: http://localhost:8080

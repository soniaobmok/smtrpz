**Необхідно для запуску додатку**:  
docker  
docker-compose  

**Для того щоб запустити**:  
cd smprtz/lab3 // перейти в директорію проекту  
docker-compose up // запустити за допомогою docker-compose  

**Для того щоб змінити конфігурації**:  
1)  docker exec -it [configserver_container_id] /bin/sh  
    // configserver_container_id можна дізнатися виконавши команду "docker ps"

2)  cd config

3)  git init

4)  echo test.prop1=1 > application.properties  
    echo test.prop5=5 >> application.properties  

5)  git add .  
    git commit -m "change config" 

Після зміни конфігурацій потрібно оновити їх за допомогою POST-методів, що описані в test.http

Eureka Server URL: http://localhost:8761  
Api Gateway URL: http://localhost:8080  
Service URL (instance 1): http://localhost:8082   
Service URL (instance 2): http://localhost:8083    
Config Server URL: http://localhost:8888  

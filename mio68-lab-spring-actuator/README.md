Используем actuator

Запускаем приложение ActuatorApplication, затем в браузере:

```
Прикладной эндпоинт:
http://localhost:8080/api/v1/hello
Смотрим что там в актуаторе?
http://localhost:8080/actuator
What beans are there in the context?
http://localhost:8080/actuator/beans
What endpoints do we have with the application?
http://localhost:8080/actuator/metrics/http.server.requests
How to get statistics for particular endpoint?
http://localhost:8080/actuator/metrics/http.server.requests?tag=uri:/api/v1/hello
```

When Spring Boot detects both Spring Boot Actuator and Spring Security, it will enable
secure access to management endpoints automatically. 
Как отключить security для актуатора? Через конфигурирование вроде как нельзя?
см. https://stackoverflow.com/questions/70717704/how-to-disable-security-just-for-actuator-health


Смотри также application.properties
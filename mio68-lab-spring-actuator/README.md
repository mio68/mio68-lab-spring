###Используем actuator

##### Запускаем приложение ActuatorApplication, затем в браузере:

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

Посмотреть настройки application.properties (из разных мест),commandLineArgs,
environment, system properties
http://localhost:8080/actuator/env

запланированные задачи
http://localhost:8080/actuator/scheduledtasks
{"cron":[],"fixedDelay":[],"fixedRate":[],"custom":[]}

Очень полезный эндпоинт.
посмотреть бины, от чего зависят ( в том числе будут показаны конфигурации которые их 
создают, см. например myConfiguration)
http://localhost:8080/actuator/beans 

Логгеры
http://localhost:8080/actuator/loggers

Посмотреть executors (чтобы выбрать конкретный надо задать тег name)
http://localhost:8080/actuator/metrics/executor.pool.size
http://localhost:8080/actuator/metrics/executor.active
Размер очереди задач
http://localhost:8080/actuator/metrics/executor.queued
http://localhost:8080/actuator/metrics/executor.queue.remaining

```

#### Настройки 
см. ```application.properties```

Доступ ко всем эндпоинтам
```
management.endpoints.web.exposure.include=*
```

#### Как показать информацию о сборке через актуатор

В ```spring-boot-maven-plugin``` добавляем ```<goal>build-info</goal>```
```xml
                <executions>
                    <execution>
                        <goals>
                            <goal>build-info</goal>
                        </goals>
                    </execution>
                </executions>

```

Чтобы была инфа про коммита git понадобится еще один плагин
https://github.com/git-commit-id/git-commit-id-maven-plugin/blob/master/docs/using-the-plugin.md
```xml
            <plugin>
                <groupId>io.github.git-commit-id</groupId>
                <artifactId>git-commit-id-maven-plugin</artifactId>
                <version>5.0.0</version>
                <executions>
                    <execution>
                        <id>get-the-git-infos</id>
                        <goals>
                            <goal>revision</goal>
                        </goals>
                        <phase>initialize</phase>
                    </execution>
                </executions>
                <configuration>
                    <generateGitPropertiesFile>true</generateGitPropertiesFile>
                    <generateGitPropertiesFilename>${project.build.outputDirectory}/git.properties</generateGitPropertiesFilename>
                    <includeOnlyProperties>
                        <includeOnlyProperty>^git.build.(time|version)$</includeOnlyProperty>
                        <includeOnlyProperty>^git.commit.id.(abbrev|full)$</includeOnlyProperty>
                    </includeOnlyProperties>
                    <commitIdGenerationMode>full</commitIdGenerationMode>
                </configuration>
            </plugin>
```

При сборке сформируются файлы ```git.properties``` и ```META-INF/build-info.properties```
Их содержимое можно посмотреть через actuator ```http://localhost:8080/actuator/info```

#### Как отключить авторизацию доступа к эндпоинтам актуатора в web?
When Spring Boot detects both Spring Boot Actuator and Spring Security, it will enable
secure access to management endpoints automatically. 
Как отключить security для актуатора? Через конфигурирование вроде как нельзя?
см. https://stackoverflow.com/questions/70717704/how-to-disable-security-just-for-actuator-health


Смотри также application.properties
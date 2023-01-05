1. Для отладки процесса создания бинов включаем журналирование 

```
<logger name="org.springframework.beans" level="DEBUG"/>
```

Пример фрагмента журнала

```
16:11:49.402 [main] DEBUG o.s.b.f.s.DefaultListableBeanFactory - Creating shared instance of singleton bean 'debugApplication'
16:11:49.402 [main] DEBUG o.s.b.f.s.DefaultListableBeanFactory - Creating shared instance of singleton bean 'debugApplicationConfiguration'
16:11:49.402 [main] DEBUG o.s.b.f.s.DefaultListableBeanFactory - Creating shared instance of singleton bean 'typeA'
16:11:49.402 [main] DEBUG o.s.b.f.s.DefaultListableBeanFactory - Creating shared instance of singleton bean 'typeB'
16:11:49.402 [main] DEBUG o.s.b.f.s.DefaultListableBeanFactory - Autowiring by type from bean name 'typeB' via constructor to bean named 'typeA'
16:11:49.402 [main] DEBUG o.s.b.f.s.DefaultListableBeanFactory - Creating shared instance of singleton bean 'anotherTypeB'
16:11:49.402 [main] DEBUG o.s.b.f.s.DefaultListableBeanFactory - Autowiring by type from bean name 'anotherTypeB' via factory method to bean named 'typeA'
```

Включение отладки для
```
<logger name="org.springframework" level="DEBUG"/>
```
может дать дополнительную информацию.

2. Запуск для отладки

Before we can attach a debugger, we must first configure the JVM to allow debugging
```
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000 -jar target/mio68-lab-spring-debug-0.0.1-SNAPSHOT.jar
```
Смотри https://www.baeldung.com/spring-debugging

3. Подключение к удаленной JVM запущенной в режиме отладки из IntlelliJ IDEA

Меню Run - Edit Configurations... - Add New Configuration - Remote JVM Debug

5. 
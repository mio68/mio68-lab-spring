## Profiling

Test application creates random MyUser and add it to list 
'''http://localhost:8080/api/v1/rnd/users'''


1. Как снять дамп памяти (https://www.baeldung.com/java-heap-dump-capture)
- подключится к работающему приложению с использованием Visual VM и снять дамп.
- локально, используя jcmd - is a very complete tool that works by sending command requests to the JVM. We have to use it in the same machine where the Java process is running.
```
  # Узнаем PID используя jcmd или jps без аргументов
  jcmd 
  # Снимаем дамп
  jcmd <pid> GC.heap_dump [options] /path/to/dump/file
```
- java -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=<file-or-dir-path>
- by using HotSpotDiagnostic MBean and access it through JConsole

2. Получение общей информации о процессе используя VisualVM
#### Open local application
Overview
PID: 35680
Host: localhost
Main class: mio68.lab.spring.profiling.Prof
Arguments: <none>

JVM: OpenJDK 64-Bit Server VM (17.0.6+10-LTS, mixed mode, sharing)
Java: version 17.0.6 2023-01-17 LTS, vendor Amazon.com Inc.
Java Home: C:\Users\Igor\.jdks\corretto-17.0.6
JVM Flags: <none>

3. Просмотр дампа памяти использую VisualVM
Возможности
-задать фильтр по пакетам, классам
-аггрегация по пакетам, классам

Как узнать кто куда входит?
1.Для каждого объекта можно узнать кто на него ссылается.
2.Можно посмотреть GC root для данного объекта

Для вычисления самого большого объекта необходимо подсчитать retained size
Вычислить retained size 
  Который показывает сколько памяти освободится при сборке этого объекта GC, если 
  см различия между shallow, retained and deep size 
  (Очень полезная статья https://www.baeldung.com/jvm-measuring-object-sizes)
When the GC reclaims the memory occupied by an object, it frees a specific amount of memory. 
That amount is the retained size of that object:

4. Размер объекта Java
12 байт заголовок
+ поля объекта (ссылка 4 байта или 8 байт)
+ добавка для выравнивания до значения кратного 8 байт. 
  Minimum object size is 16 bytes for modern 64-bit JDK since the object has 12-byte header,
  padded to a multiple of 8 bytes. In 32-bit JDK, the overhead is 8 bytes, padded to a multiple
  of 4 bytes.

Пример расчета c использованием JOL
mio68.lab.spring.profiling.model.MyUser object internals:
OFFSET  SIZE               TYPE DESCRIPTION                               VALUE
0    12                    (object header)                           N/A
12     4     java.lang.Long MyUser.id                                 N/A
16     4   java.lang.String MyUser.name                               N/A
20     4                    (loss due to the next object alignment)
Instance size: 24 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total


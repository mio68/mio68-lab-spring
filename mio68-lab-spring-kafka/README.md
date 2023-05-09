
#### Prepare

https://hub.docker.com/r/bitnami/kafka

```
docker-compose up -d
```

#### Logging

По умолчанию журналируется с уровнем INFO следующее:
```
2023-05-01 11:04:46.382  INFO 20724 --- [           main] o.a.k.clients.consumer.ConsumerConfig    : ConsumerConfig values: 
	allow.auto.create.topics = true
	auto.commit.interval.ms = 5000
	auto.offset.reset = latest
	bootstrap.servers = [localhost:29092]
	check.crcs = true
	client.dns.lookup = use_all_dns_ips
	client.id = consumer-test-group-2
```

Назначение партиций и смещений
```
2023-05-01 11:04:49.859  INFO 20724 --- [Container-1-C-1] s.k.l.ConcurrentMessageListenerContainer : test-group: partitions assigned: [test_topic-1]
2023-05-01 11:04:49.859  INFO 20724 --- [Container-0-C-1] s.k.l.ConcurrentMessageListenerContainer : test-group: partitions assigned: [test_topic-0]
2023-05-01 11:04:49.878  INFO 20724 --- [Container-0-C-1] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-test-group-1, groupId=test-group] Setting offset for partition test_topic-0 to the committed offset FetchPosition{offset=3, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[localhost:29092 (id: 1 rack: null)], epoch=0}}
2023-05-01 11:04:49.878  INFO 20724 --- [Container-1-C-1] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-test-group-2, groupId=test-group] Setting offset for partition test_topic-1 to the committed offset FetchPosition{offset=2, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[localhost:29092 (id: 1 rack: null)], epoch=0}}
```

Для вывода дополнительной информации по консюмеру можно задать
org.springframework.kafka.listener.ContainerProperties.setLogContainerConfig(true)
```
2023-05-01 11:14:45.660  INFO 25640 --- [           main] o.s.k.l.KafkaMessageListenerContainer    : KafkaMessageListenerContainer.ListenerConsumer [
containerProperties=ContainerProperties [
 topics=[test_topic]
 pollTimeout=3210
 groupId=test-group
```

Логирование полинга
```
logging.level.org.springframework.kafka.listener.KafkaMessageListenerContainer=DEBUG
```

```
2023-05-01 11:34:23.951 DEBUG 25444 --- [Container-0-C-1] o.s.k.l.KafkaMessageListenerContainer    : Received: 0 records
2023-05-01 11:34:23.952 DEBUG 25444 --- [Container-0-C-1] o.s.k.l.KafkaMessageListenerContainer    : Commit list: {}
2023-05-01 11:34:23.955 DEBUG 25444 --- [Container-1-C-1] o.s.k.l.KafkaMessageListenerContainer    : Received: 0 records
2023-05-01 11:34:23.955 DEBUG 25444 --- [Container-1-C-1] o.s.k.l.KafkaMessageListenerContainer    : Commit list: {}
2023-05-01 11:34:27.178 DEBUG 25444 --- [Container-1-C-1] o.s.k.l.KafkaMessageListenerContainer    : Received: 0 records
2023-05-01 11:34:27.178 DEBUG 25444 --- [Container-0-C-1] o.s.k.l.KafkaMessageListenerContainer    : Received: 0 records
2023-05-01 11:34:27.178 DEBUG 25444 --- [Container-1-C-1] o.s.k.l.KafkaMessageListenerContainer    : Commit list: {}
2023-05-01 11:34:27.178 DEBUG 25444 --- [Container-0-C-1] o.s.k.l.KafkaMessageListenerContainer    : Commit list: {}
```

#### Monitoring

Dependencies are required for actuator
```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
```
Properties for Container by default
```
#mio68.lab.spring.kafka.consumer.properties.micrometerEnabled=true
#mio68.lab.spring.kafka.consumer.properties.observationEnabled=false
```
Enable metrics (application.properties)
```
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always
```

Metrics  endpoint ```http://localhost:8080/actuator/metrics/spring.kafka.listener```
есть счетчик и тайминги
можно фильтровать по тегам
```
http://localhost:8080/actuator/metrics/spring.kafka.listener?tag=name:concurrentMessageListenerContainer-0
http://localhost:8080/actuator/metrics/spring.kafka.listener?tag=result:success
http://localhost:8080/actuator/metrics/spring.kafka.listener?tag=result:failure
http://localhost:8080/actuator/metrics/spring.kafka.listener?tag=exception:ListenerExecutionFailedException
http://localhost:8080/actuator/metrics/spring.kafka.listener?tag=result:success&tag=name:concurrentMessageListenerContainer-0
```

#### Application events

Create an application event listener, like this:
```java
@Component
@Slf4j
public class KafkaEventListener implements ApplicationListener<KafkaEvent> {

    @Override
    public void onApplicationEvent(KafkaEvent event) {
        log.info("event: {}", event);
    }

}
```

In milliseconds
```mio68.lab.spring.kafka.consumer.properties.idleEventInterval=30000```
If set it will report the following (if it's not set when no idle reporting)
```
2023-05-01 14:00:36.063  INFO 24044 --- [Container-0-C-1] m.l.s.kafka.event.KafkaEventListener     : event: ListenerContainerIdleEvent [idleTime=90.319s, listenerId=concurrentMessageListenerContainer-0, container=KafkaMessageListenerContainer [id=concurrentMessageListenerContainer-0, clientIndex=-0, topicPartitions=[test_topic-0]], paused=false, topicPartitions=[test_topic-0]]
2023-05-01 14:00:36.079  INFO 24044 --- [Container-1-C-1] m.l.s.kafka.event.KafkaEventListener     : event: ListenerContainerIdleEvent [idleTime=90.153s, listenerId=concurrentMessageListenerContainer-1, container=KafkaMessageListenerContainer [id=concurrentMessageListenerContainer-1, clientIndex=-1, topicPartitions=[test_topic-1]], paused=false, topicPartitions=[test_topic-1]]
```

For long consuming messages ```NonResponsiveConsumerEvent``` is produced.
```
2023-05-01 14:23:44.919  INFO 23564 --- [Container-0-C-1] m.l.s.kafka.event.KafkaEventListener     : event: ListenerContainerNoLongerIdleEvent [idleTime=50.942s, listenerId=concurrentMessageListenerContainer-0, container=KafkaMessageListenerContainer [id=concurrentMessageListenerContainer-0, clientIndex=-0, topicPartitions=[test_topic-0]], topicPartitions=[test_topic-0]]
2023-05-01 14:23:44.920  INFO 23564 --- [Container-0-C-1] m.l.s.k.consumer.SimpleMessageListener   : onMessage key: [k1] value: [sleep:15000]
2023-05-01 14:23:53.337  INFO 23564 --- [TaskScheduler-1] m.l.s.kafka.event.KafkaEventListener     : event: NonResponsiveConsumerEvent [timeSinceLastPoll=10.382s, listenerId=concurrentMessageListenerContainer-0, container=KafkaMessageListenerContainer [id=concurrentMessageListenerContainer-0, clientIndex=-0, topicPartitions=[test_topic-0]], topicPartitions=[test_topic-0]]
2023-05-01 14:24:03.178  INFO 23564 --- [Container-0-C-1] m.l.s.kafka.event.KafkaEventListener     : event: ListenerContainerIdleEvent [idleTime=18.259s, listenerId=concurrentMessageListenerContainer-0, container=KafkaMessageListenerContainer [id=concurrentMessageListenerContainer-0, clientIndex=-0, topicPartitions=[test_topic-0]], paused=false, topicPartitions=[test_topic-0]]
```

#### Обработка ошибок
По умолчанию повторяет 9 раз, на 10-й делает коммит пропуская необработанное сообщение
```
2023-05-01 14:34:21.482 DEBUG 19700 --- [Container-0-C-1] o.s.k.l.KafkaMessageListenerContainer    : Received: 1 records
2023-05-01 14:34:21.482  INFO 19700 --- [Container-0-C-1] m.l.s.k.consumer.SimpleMessageListener   : onMessage key: [k1] value: [sleep:150w]
2023-05-01 14:34:21.482 DEBUG 19700 --- [Container-0-C-1] o.s.k.l.KafkaMessageListenerContainer    : Commit list: {}
2023-05-01 14:34:21.483 ERROR 19700 --- [Container-0-C-1] o.s.kafka.listener.DefaultErrorHandler   : Backoff FixedBackOff{interval=0, currentAttempts=10, maxAttempts=9} exhausted for test_topic-0@25
...
2023-05-01 14:34:21.484 DEBUG 19700 --- [Container-0-C-1] o.s.k.l.KafkaMessageListenerContainer    : Commit list: {test_topic-0=OffsetAndMetadata{offset=26, leaderEpoch=null, metadata=''}}
2023-05-01 14:34:21.484 DEBUG 19700 --- [Container-0-C-1] o.s.k.l.KafkaMessageListenerContainer    : Committing: {test_topic-0=OffsetAndMetadata{offset=26, leaderEpoch=null, metadata=''}}
```
Обработка продолжается далее.
Генерируется исключение
```org.springframework.kafka.listener.ListenerExecutionFailedException```.

Наличие таких будет видно в актуаторе
```
http://localhost:8080/actuator/metrics/spring.kafka.listener?tag=exception:ListenerExecutionFailedException
```

KafkaListenerErrorHandler implementation handles exceptions for MessageListener.
It's possible to setup it with @KafkaListener annotation (has a new attribute: errorHandler).

CommonErrorHandler implementation handles exceptions for container.
```
org.springframework.kafka.listener.AbstractMessageListenerContainer.setCommonErrorHandler(@Nullable CommonErrorHandler commonErrorHandler)
```


The DefaultErrorHandler considers certain exceptions to be fatal, and retries are skipped for such exceptions; the recoverer is invoked on the first failure. 
The exceptions that are considered fatal, by default, are:
* DeserializationException
* MessageConversionException
* ConversionException
* MethodArgumentResolutionException
* NoSuchMethodException
* ClassCastException

You can add more exception types to the not-retryable category.

#### Ошибки недоступности брокера

По умолчанию логирует слишком часто! Примерно раз в секунду
```
2023-05-09 11:10:18.404  INFO 19640 --- [Container-0-C-1] org.apache.kafka.clients.NetworkClient   : [Consumer clientId=consumer-test-group-1, groupId=test-group] Node 1 disconnected.
2023-05-09 11:10:18.404  WARN 19640 --- [Container-0-C-1] org.apache.kafka.clients.NetworkClient   : [Consumer clientId=consumer-test-group-1, groupId=test-group] Connection to node 1 (localhost/127.0.0.1:29092) could not be established. Broker may not be available.
2023-05-09 11:10:19.258  INFO 19640 --- [Container-0-C-1] org.apache.kafka.clients.NetworkClient   : [Consumer clientId=consumer-test-group-1, groupId=test-group] Node 1 disconnected.
2023-05-09 11:10:19.258  WARN 19640 --- [Container-0-C-1] org.apache.kafka.clients.NetworkClient   : [Consumer clientId=consumer-test-group-1, groupId=test-group] Connection to node 1 (localhost/127.0.0.1:29092) could not be established. Broker may not be available.
```
Думаю надо установить уровень ```org.apache.kafka.clients.NetworkClient: ERROR```
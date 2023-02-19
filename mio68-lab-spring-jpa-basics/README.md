#### Learning JPA basics

1. Connection is defined within application.properties

```
spring.datasource.url=
spring.datasource.username= 
spring.datasource.password=
```

2. JPA properties

```
# Initialize the schema on startup, default is false.
spring.jpa.generate-ddl=true
```

3. Logging SQL and it's parameters, and logging transactions boundaries

```
# Send SQL to standard out
#spring.jpa.show-sql=true

# Log SQL with logger
logging.level.org.hibernate.SQL=DEBUG

# Log SQL parameters with logger
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE

# Format SQL
spring.jpa.properties.hibernate.format_sql=true

# Enable Spring's transaction logs
logging.level.org.springframework.transaction.interceptor = TRACE

# Enable logging for JPA
logging.level.org.springframework.orm.jpa=DEBUG
  
# Enable logging for transactions   
logging.level.org.springframework.transaction=DEBUG 
logging.level.org.hibernate.transaction=DEBUG
logging.level.org.hibernate.engine.transaction.internal=DEBUG
```

4. Когда EntityManager::contains возвращает true для данного entity?

По результатам работы кода (см. DemoApplicationRunner)

Только тогда когда в текущей транзакции этот entity находится в контексте (он же кэш 
первого уровня). Если Entity в данной транзакции еще не создавался, не мержился, не был найден 
при помощи find, то EntityManager::contains возвращает false. 

Если операции над контекстом выполняются без транзакции, например поиск, а затем проверка 
наличия в контексте, то в этих опытах получено значение false. Вызовы контекста выполняются 
вне транзакции и после нахождения entity вовсе не обязательно оставлять его в контексте, что логично! 

5. Применение @Transactional

Имеет смысл только когда метод требует атомарного выполнения нескольких вызовов 
репозитория или непосредственно EntityManager, см. например DemoService::testContextContainsEntityWithId1

```
    @Transactional
    public void testContextContainsEntityWithId1() {
        Customer firstCustomerFound = customerRepository.find(1L);
        log.info("customer: " + firstCustomerFound);
        boolean contextContainsFirstCustomer = customerRepository.contextContains(firstCustomerFound);
        log.info("customerRepository.contextContains(firstCustomerFound): " + contextContainsFirstCustomer);
    }
```

или CustomCustomerRepositoryImpl::testContextContainsEntity

```
    @Override
    @Transactional
    public void testContextContainsEntity() {
        Customer customer = entityManager.find(Customer.class, Long.valueOf(1L));
        log.info("customer: " + customer);
        boolean contextContainsIt = entityManager.contains(customer);
        log.info("entityManager.contains(customer): " + contextContainsIt); // true
    }
```

6. Обновление сущностей в транзакции не требует делать merge или update

```
    // With @Transactional annotation this method updates entity quite well
    // Without annotation it just updates entity name property but doesn't flush it back to DB
    @Transactional
    public void updateEntityWithId1() {
        Customer firstCustomerFound = customerRepository.find(1L);
        log.info("customer: " + firstCustomerFound);
        firstCustomerFound.setName(
                String.format(
                        "%s updated at: %s",
                        "John Doe",
                        LocalDateTime.now()));
    }
```
Flush для PersistenceContext будет вызван или перед выполнением запросов или при коммите
транзакции.

Необходимо контролировать отладку SQL чтобы видеть там 

```
    update
        customer 
    set
        email=?,
        name=? 
    where
        id=?
```


7. Вопросы
Q.Обновление Entity. Достаточно ли просто установить сеттерами свойства и ожидать 
что при завершении транзакции будет flush?
A. Да. For managed entities, you don’t need any save method because Hibernate automatically synchronizes 
the entity state with the underlying database record.

Q. Нужен ли @Transactional если вызываем только один метод EntityManager?
Если метод не @Transactional и вызван из транзакции?
Как учитывает транзакции EntityManager?
A. 

8. 
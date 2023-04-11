### Learning time field of entity table auto generations and converting

1. Field of java.sql.Timestamp
JPA generates
```
   create table account_entity (
   id int8 not null,
   created_on timestamp,
   name varchar(255),
   primary key (id)
   )
```

2. Missed @Entity annotation
```
org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'accountRepository' defined in mio68.lab.spring.jpa.timefield.repository.AccountRepository defined in @EnableJpaRepositories declared on JpaRepositoriesRegistrar.EnableJpaRepositoriesConfiguration: Invocation of init method failed; nested exception is java.lang.IllegalArgumentException: Not a managed type: class mio68.lab.spring.jpa.timefield.entity.Account
...
Caused by: java.lang.IllegalArgumentException: Not a managed type: class mio68.lab.spring.jpa.timefield.entity.Account
```

3. Field of java.time.Instant
   JPA generates
```
    create table account (
       id int8 not null,
        created_on timestamp,
        name varchar(255),
        primary key (id)
    )
```

4. 
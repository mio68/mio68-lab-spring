1. Use H2 when testing

Include dependency with scope test:
```
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>test</scope>
        </dependency>
```

2. Disable caching with when testing.

Use testing property: 
```
spring.datasource.type=org.springframework.jdbc.datasource.SimpleDriverDataSource
```

3. DataSource bean in the context 

Name of bean is ```dataSource```
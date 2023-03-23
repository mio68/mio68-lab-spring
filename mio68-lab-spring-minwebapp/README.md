## Minimal Web application with books database.

1. It uses H2 embedded database.
2. All classes are placed into the MinimalWebApplication.java
3. JdbcTemplate is used to deal with database.
4. Schema creation and initial date are provided with schema.sql and data.sql resources.
5. Browse books list  
```
http://localhost:8080/api/v1/books
```
6. Post new book
```
curl -X POST -H "Content-Type: application/json" -d "{\"isbn\":\"978-1-4842-2789-3\",\"title\":\"Spring 5 Recipes: A Problem-Solution Approach\",\"authors\":\"Marten Deinum, Daniel Rubio, Josh Long\"}" http://localhost:8080/api/v1/book 
```

7. Log http request and parameters
   
https://stackoverflow.com/questions/53723613/how-to-set-enableloggingrequestdetails-true-in-spring-boot

Add to application properties 
```
logging.level.org.springframework.web.servlet.DispatcherServlet=TRACE
spring.mvc.log-request-details=true
```
Log example
```
2023-03-22 18:34:25.399 TRACE 16344 --- [nio-8080-exec-2] o.s.web.servlet.DispatcherServlet        : GET "/api/v1/books", parameters={}, headers={content-type:[application/json], user-agent:[PostmanRuntime/7.28.4], accept:[*/*], postman-token:[3d58fb89-839b-460a-a698-81d7e7041ec9], host:[localhost:8080], accept-encoding:[gzip, deflate, br], connection:[keep-alive]} in DispatcherServlet 'dispatcherServlet'
```
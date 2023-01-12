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

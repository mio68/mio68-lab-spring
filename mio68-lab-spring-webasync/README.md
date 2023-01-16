### Async web application with usual spring-boot-starter-web and Tomcat.

1. Dependency 
Just the same as for regular web app.

```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>

```

2. Tests
Usual @WebMvcTest with MockMvc, but async dispathc is required
```
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/hello/callable"))
                .andExpect(request().asyncStarted())
                .andDo(MockMvcResultHandlers.log())
                .andReturn();
        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(TEXT_PLAIN))
                .andExpect(content().string(startsWith("Hello World")))
                .andExpect(content().string(containsStringIgnoringCase("callable")));

```

3. How to see arriving messages?
Try
```
curl http://localhost:8080/api/v1/msg/emit
```

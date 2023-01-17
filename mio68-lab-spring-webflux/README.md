## Reactive Web App

1. Dependency

```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>
```

2. It's still possible to use CompletableFuture with webflux.

```
    @GetMapping("/hello/completable")
    public CompletableFuture<String> helloWithCompletable() {
        log.info("say hello with CompletableFuture<String>");
        return CompletableFuture.supplyAsync( () -> {
                    randomDelay();
                    return "Hello World from CompletableFuture!";
                },
                taskExecutor);
    }
```

3. Tests

Dependency: spring-boot-starter-test
Use @WebFluxTest to test a controller or set of controllers 
Hamcrest is can be used of course. Look at the example.
```
    public void whenHelloWithMono_thenOk() {
        webTestClient.get()
                .uri("/api/v1/hello/mono")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                // Hamcrest matcher can be used here!
                .value(startsWith("Hello World"));
    }
```




package mio68.lab.spring.webflux.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class HelloController {


    public HelloController() {
    }

    @GetMapping("/hello/mono")
    public Mono<String> helloWithMono() {
        log.info("say hello with Mono<String>");
        return Mono.just("Hello World from reactive web app!");
    }

    @GetMapping("/hello/mono/no_content")
    public Mono<String> helloWithMonoNoContent() {
        log.info("say hello with Mono<String> and NO_CONTENT");
        return Mono.empty();
    }

    @GetMapping("/hello/completable")
    public CompletableFuture<String> helloWithCompletable() {
        log.info("say hello with CompletableFuture<String>");
        return CompletableFuture.supplyAsync( () -> {
                    randomDelay();
                    return "Hello World from CompletableFuture!";
                });
    }

    @GetMapping("/hello/string")
    public String helloWithString() {
        log.info("say hello with String");
        return "Hello World by String!";
    }

    @GetMapping("/hello/response_entity")
    public ResponseEntity<String> helloWithResponseEntity() {
        log.info("say hello with ResponseEntity<String>");
        return new ResponseEntity<>("Hello World with ResponseEntity<String>!", HttpStatus.OK);
    }

    @GetMapping("/hello/response_entity/no_content")
    public ResponseEntity<String> helloEmptyResponseEntity() {
        log.info("say hello empty with ResponseEntity<String>");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void randomDelay() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

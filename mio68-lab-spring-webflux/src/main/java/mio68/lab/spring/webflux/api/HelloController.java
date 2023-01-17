package mio68.lab.spring.webflux.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;
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

    @GetMapping("/hello/completable")
    public CompletableFuture<String> helloWithCompletable() {
        log.info("say hello with CompletableFuture<String>");
        return CompletableFuture.supplyAsync( () -> {
                    randomDelay();
                    return "Hello World from CompletableFuture!";
                });
    }


    private void randomDelay() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

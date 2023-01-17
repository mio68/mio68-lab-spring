package mio68.lab.spring.webasync.api;

import lombok.extern.slf4j.Slf4j;
import mio68.lab.spring.webasync.model.Msg;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class HelloController {

    private final TaskExecutor taskExecutor;

    public HelloController(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    /*
     * This endpoint works just fine!
     */
    @GetMapping("/hello/callable")
    public Callable<String> helloCallable() {
        return () -> {
            log.info("get greeting with Callable<String>");
            randomDelay();
            return "Hello World from Callable<String>!";
        };
    }

    /*
     * This endpoint works just fine too!
     */
    @GetMapping("/hello/completable")
    public CompletableFuture<String> helloCompletable() {
        return CompletableFuture.supplyAsync(() -> {
            log.info("get greeting with CompletableFuture<String>");
            randomDelay();
            return "Hello World from CompletableFuture<String>!";
        }, taskExecutor);
    }

    @GetMapping("/msg/emit")
    public ResponseBodyEmitter emit10() {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();
        taskExecutor.execute(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    Msg msg = Msg.create(String.format("Hello World! Repeat counter [%d].", i));
                    randomDelay();
                    emitter.send(msg); // Send
                }
                emitter.complete(); // Complete
            } catch (IOException e) {
                emitter.completeWithError(e); // Complete with error
            }
        });
        return emitter;
    }

    @GetMapping("/msg/sse")
    public SseEmitter emitSse() {
        SseEmitter emitter = new SseEmitter();
        taskExecutor.execute(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    Msg msg = Msg.create(String.format("Hello World! Repeat counter [%d].", i));
                    randomDelay();
                    emitter.send(msg); // Send
                }
                emitter.complete(); // Complete
            } catch (IOException e) {
                emitter.completeWithError(e); // Complete with error
            }
        });
        return emitter;
    }

    private void randomDelay() {
        try {
            TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(2000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}

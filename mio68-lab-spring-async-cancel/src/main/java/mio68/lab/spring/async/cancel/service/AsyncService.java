package mio68.lab.spring.async.cancel.service;

import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface AsyncService {

    CompletableFuture<String> submit(long iterationNumber, int delaySec);

    Future<String> submitWithFuture(long iterationNumber, int delaySec);

    Future<String> submitWithTaskExecutor(long iterationNumber, int delaySec);
}

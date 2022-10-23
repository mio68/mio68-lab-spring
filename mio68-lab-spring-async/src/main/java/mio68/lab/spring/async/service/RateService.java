package mio68.lab.spring.async.service;

import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface RateService {

    @Async
    CompletableFuture<Integer> getRateFuture() throws InterruptedException;

    Integer getRate() throws InterruptedException;

    int getSuppliersCounter() throws InterruptedException;
}

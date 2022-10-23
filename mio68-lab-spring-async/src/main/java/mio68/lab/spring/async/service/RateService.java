package mio68.lab.spring.async.service;

import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface RateService {

    Integer getRate() throws InterruptedException;

    int getSuppliersCounter() throws InterruptedException;
}

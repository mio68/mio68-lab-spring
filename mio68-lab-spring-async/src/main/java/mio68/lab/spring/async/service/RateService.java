package mio68.lab.spring.async.service;

import mio68.lab.spring.async.exception.ApplicationExceptionForThrow;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface RateService {

    CompletableFuture<Integer> getRateFuture(Mode mode) throws ApplicationExceptionForThrow;

    CompletableFuture<Integer> getRateFuture() throws InterruptedException;

    Integer getRate() throws InterruptedException;

    int getSuppliersCounter() throws InterruptedException;
}

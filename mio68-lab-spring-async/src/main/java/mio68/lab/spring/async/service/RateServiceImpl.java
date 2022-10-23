package mio68.lab.spring.async.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RateServiceImpl implements RateService {

    // Got it! This is right way to return value from @Async annotated method.
    @Override
    @Async
    public CompletableFuture<Integer> getRateFuture() throws InterruptedException {
        log.info("Lets try to get current rate for future using...");
        TimeUnit.SECONDS.sleep(7);
        int rate = 31;
        log.info("Rate [{}] successfully obtained for future using!%n", rate);
        return CompletableFuture.completedFuture(rate);
    }

    // This method returns NULL to client, and performs asynchronously, but it's actual return value
    // never passed to it's client
    @Override
    @Async
    public Integer getRate() throws InterruptedException {
        log.info("Lets try to get current rate...");
        TimeUnit.SECONDS.sleep(10);
        int rate = 31;
        log.info("Rate [{}] successfully obtained!", rate);
        return rate;
    }

//    I don't know how to use @Async with CompletableFuture
//    @Override
//    @Async
//    public void getRate(CompletableFuture<Integer> completableFuture) throws InterruptedException {
//        System.out.println("Lets try to get current rate with completable future...");
//        TimeUnit.SECONDS.sleep(3);
//        int rate = 31;
//        System.out.println(String.format("Rate [%s] successfully obtained with completable future!", rate));
//        completableFuture.complete(31);
//    }

    // Bad idea to return primitive value.
    @Override
    @Async
    public int getSuppliersCounter() throws InterruptedException {
        log.info("Lets try to count suppliers available...");
        TimeUnit.SECONDS.sleep(5);
        int counter = 7;
        log.info("Available suppliers counter is [{}].%n", counter);
        return counter;
    }

}

package mio68.lab.spring.async.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RateServiceImpl implements RateService {

    // This method returns NULL to client, and performs asynchronously, but it's actual return value
    // never passed to it's client
    @Override
    @Async
    public Integer getRate() throws InterruptedException {
        System.out.println("Lets try to get current rate...");
        TimeUnit.SECONDS.sleep(10);
        int rate = 31;
        System.out.printf("Rate [%s] successfully obtained!%n", rate);
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

    // Bad idea to return primitive value
    @Override
    @Async
    public int getSuppliersCounter() throws InterruptedException {
        System.out.println("Lets try to count suppliers available...");
        TimeUnit.SECONDS.sleep(7);
        int counter = 7;
        System.out.printf("Available suppliers counter is [%s].%n", counter);
        return counter;
    }

}

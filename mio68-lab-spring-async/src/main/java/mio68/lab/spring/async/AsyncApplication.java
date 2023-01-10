package mio68.lab.spring.async;

import lombok.extern.slf4j.Slf4j;
import mio68.lab.spring.async.service.HelloService;
import mio68.lab.spring.async.service.Mode;
import mio68.lab.spring.async.service.RateService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableAsync
@Slf4j
public class AsyncApplication {

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext applicationContext =
                SpringApplication.run(AsyncApplication.class, args);
        System.out.println("Press [ENTER] to quit:");
        System.in.read();
        SpringApplication.exit(applicationContext);
    }

    // This runner starts asynchronous sayHello method quite well!
    @Bean
    @Order(1)
    public ApplicationRunner startupRunner(HelloService hello) {
        return (args) -> hello.sayHello();
    }

    // This runner starts asynchronous method and gets NULL, not actual result.
    // It's not good idea!
    @Bean
    @Order(2)
    public ApplicationRunner startupRunner2(RateService rateService) {
        return (args) -> log.info("Current rate is: [{}]", rateService.getRate());
    }

    // This runner starts asynchronous method and causes Exception, not actual result.
    // It's not good idea too.
//    @Bean
//    @Order(3)
//    public ApplicationRunner startupRunner3(RateService rateService) {
//        return (args) -> {
//            log.ingo("Supplier counter is: [{}]", rateService.getSuppliersCounter());};
//    }

    @Bean
    @Order(4)
    public ApplicationRunner startupRunner4(RateService rateService) {
        return (args) -> {
            CompletableFuture<Integer> rate = rateService.getRateFuture();
            log.info("Got rate future: [{}]", rate);
            log.info("Current rate(from future) is: [{}]", rate.get(10, TimeUnit.SECONDS));};
    }

    @Bean
    @Order(5)
    public ApplicationRunner startupRunner5(RateService rateService) {
        return (args) -> {
            CompletableFuture<Integer> rate = rateService.getRateFuture(Mode.NORMAL_COMPLETE);
            log.info("normal complete got rate [{}]", rate.get());

            try {
                rate = rateService.getRateFuture(Mode.EXCEPTIONALLY_COMPLETE);
                int r = rate.get();
            } catch (Exception ex) {
/* Log example:
java.util.concurrent.ExecutionException: mio68.lab.spring.async.exception.ApplicationExceptionForCatch: competed with exception
	at java.base/java.util.concurrent.CompletableFuture.reportGet(CompletableFuture.java:396) ~[na:na]
	at java.base/java.util.concurrent.CompletableFuture.get(CompletableFuture.java:2073) ~[na:na]
	at mio68.lab.spring.async.AsyncApplication.lambda$startupRunner5$3(AsyncApplication.java:74) ~[classes/:na]
...
Caused by: mio68.lab.spring.async.exception.ApplicationExceptionForCatch: competed with exception
	at mio68.lab.spring.async.service.RateServiceImpl.getRateFuture(RateServiceImpl.java:32) ~[classes/:na]
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77) ~[na:na]
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
	at java.base/java.lang.reflect.Method.invoke(Method.java:568) ~[na:na]
*/
                log.info("exceptionally complete", ex);
            }

            try {
                rate = rateService.getRateFuture(Mode.THROW_APPLICATION_EXCEPTION);
                int r = rate.get();
            } catch (Exception ex) {
/*
 Log example:
 java.util.concurrent.ExecutionException: mio68.lab.spring.async.exception.ApplicationExceptionForThrow: not handled by get rate
	at java.base/java.util.concurrent.CompletableFuture.reportGet(CompletableFuture.java:396) ~[na:na]
	at java.base/java.util.concurrent.CompletableFuture.get(CompletableFuture.java:2073) ~[na:na]
	at mio68.lab.spring.async.AsyncApplication.lambda$startupRunner5$3(AsyncApplication.java:81) ~[classes/:na]
 ...
Caused by: mio68.lab.spring.async.exception.ApplicationExceptionForThrow: not handled by get rate
	at mio68.lab.spring.async.service.RateServiceImpl.getRateFuture(RateServiceImpl.java:28) ~[classes/:na]
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77) ~[na:na]
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
	at java.base/java.lang.reflect.Method.invoke(Method.java:568) ~[na:na]
*/
                log.info("async method throws exception", ex);
            }

            try {
                rate = rateService.getRateFuture(Mode.THROW_RUNTIME_EXCEPTION);
                int r = rate.get();
            } catch (Exception ex) {
/* Log example:
 java.util.concurrent.ExecutionException: java.lang.RuntimeException: get rate got something unexpected
...
Caused by: java.lang.RuntimeException: get rate got something unexpected
	at mio68.lab.spring.async.service.RateServiceImpl.getRateFuture(RateServiceImpl.java:30) ~[classes/:na]
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77) ~[na:na]
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
	at java.base/java.lang.reflect.Method.invoke(Method.java:568) ~[na:na]
*/
                log.info("async method throws runtime", ex);
            }

        };
    }



}

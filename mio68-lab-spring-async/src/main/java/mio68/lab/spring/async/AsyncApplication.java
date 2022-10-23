package mio68.lab.spring.async;

import lombok.extern.slf4j.Slf4j;
import mio68.lab.spring.async.service.HelloService;
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

}

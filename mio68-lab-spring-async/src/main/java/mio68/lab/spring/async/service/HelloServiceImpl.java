package mio68.lab.spring.async.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class HelloServiceImpl implements HelloService {

    @Async
    @Override
    public void sayHello() throws InterruptedException {
        log.info("sayHello begins...");
        TimeUnit.SECONDS.sleep(5);
        System.out.println("Hello World!");
        log.info("sayHello finished.");
    }
}

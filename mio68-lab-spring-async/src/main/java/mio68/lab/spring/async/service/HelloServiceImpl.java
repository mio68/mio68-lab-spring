package mio68.lab.spring.async.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class HelloServiceImpl implements HelloService {

    @Async
    @Override
    public void sayHello() throws InterruptedException {
        System.out.println("sayHello begin");
        TimeUnit.SECONDS.sleep(5);
        System.out.println("Hello World!");
        System.out.println("sayHello finished");
    }
}

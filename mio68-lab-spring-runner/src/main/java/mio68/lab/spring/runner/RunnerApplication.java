package mio68.lab.spring.runner;

import mio68.lab.spring.runner.service.HelloService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class RunnerApplication {
    public static void main(String[] args) {
        SpringApplication.run(RunnerApplication.class, args);
    }

    @Component
    public static class HelloServiceInvoker implements ApplicationRunner {
        private final HelloService helloService;

        public HelloServiceInvoker(HelloService helloService) {
            this.helloService = helloService;
        }

        @Override
        public void run(ApplicationArguments args) {
            System.out.println(helloService.hello());
        }
    }
}

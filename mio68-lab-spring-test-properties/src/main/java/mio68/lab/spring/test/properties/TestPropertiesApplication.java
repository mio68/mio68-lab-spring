package mio68.lab.spring.test.properties;

import mio68.lab.spring.test.properties.bean.HelloBean;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TestPropertiesApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestPropertiesApplication.class, args);
    }

    @Bean
    public ApplicationRunner sayHello(HelloBean helloBean) {
        return args -> helloBean.sayHello();
    }
}

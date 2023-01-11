package mio68.lab.spring.test.properties.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HelloBean {

    private final String greeting;

    public HelloBean(@Value("${hello.msg}") String greeting) {
        this.greeting = greeting;
    }

    public void sayHello() {
        log.info(greeting);
    }

    public String getGreeting() {
        return greeting;
    }
}

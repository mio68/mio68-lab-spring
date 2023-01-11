package mio68.lab.spring.test.properties;

import mio68.lab.spring.test.properties.bean.HelloBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestPropertiesApplicationTest {

    @Autowired
    HelloBean helloBean;

    // If test/resources/application.properties is present then properties come from it.
    @Test
    public void whenApplicationProperties_thanGreetingComesFromThem() {
        assertEquals("Hello Test!", helloBean.getGreeting());
    }
}
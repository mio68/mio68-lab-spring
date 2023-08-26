package mio68.lab.spring.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

// This test loads full application context (components, services, repository),
// include application runners.
@SpringBootTest
class TestApplicationTest {

    @Autowired
    ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        ApplicationContextHelper.printInfo(applicationContext);
    }

    @Test
    void test() {

    }
}

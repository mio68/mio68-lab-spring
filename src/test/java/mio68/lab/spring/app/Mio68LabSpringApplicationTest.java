package mio68.lab.spring.app;

import mio68.lab.spring.app.model.Person;
import mio68.lab.spring.app.service.PersonService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Whole application context is loaded.
 */
@SpringBootTest(classes = {Mio68LabSpringApplication.class})
class Mio68LabSpringApplicationTest {

    @Autowired
    PersonService personService;

    @Autowired
    ApplicationContext applicationContext;

    @Test
    void printBeanNames() {
        System.out.println("Bean definitions count: " +
                applicationContext.getBeanDefinitionCount());
        System.out.println("Bean definition names: ");
        for (String beanName : applicationContext.getBeanDefinitionNames()) {
            System.out.println(beanName);
        }
    }

    @Test
    public void savePerson() {
       personService.save(new Person("John", "Doe"));
    }

}
package mio68.lab.spring.webapp;

import mio68.lab.spring.webapp.common.model.Person;
import mio68.lab.spring.webapp.secureapp.SecureApplication;
import mio68.lab.spring.webapp.common.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Whole application context is loaded.
 */
@SpringBootTest(classes = {SecureApplication.class})
class SecureApplicationTest {

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
        Person person = new Person("John", "Doe");
        personService.save(person);
        List<Person> persons = personService.list();
        assertTrue(persons.contains(person), "saved person must be returned by list");
    }

}
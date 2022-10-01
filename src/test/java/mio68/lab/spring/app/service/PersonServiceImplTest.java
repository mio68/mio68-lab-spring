package mio68.lab.spring.app.service;

import mio68.lab.spring.app.map.PersonMapper;
import mio68.lab.spring.app.model.Person;
import mio68.lab.spring.app.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Integration test for PersonServiceImpl.
 * It's dependencies are created by @MockBean
 */
@SpringBootTest(classes = {PersonServiceImpl.class})
class PersonServiceImplTest {

    @MockBean
    PersonRepository personRepository;

    @MockBean
    PersonMapper personMapper;

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
        when(personRepository.save(any())).thenReturn(1);
        verify(personRepository, atMostOnce()).save(any());
        personService.save(new Person("John", "Doe"));
    }

}
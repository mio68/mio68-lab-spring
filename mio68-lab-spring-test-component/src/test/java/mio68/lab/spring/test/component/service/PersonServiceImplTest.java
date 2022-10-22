package mio68.lab.spring.test.component.service;


import mio68.lab.spring.test.component.dao.PersonDto;
import mio68.lab.spring.test.component.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test ONLY for PersonServiceImpl.
 * It's dependencies are created by @MockBean
 */
@SpringBootTest(classes = {PersonServiceImpl.class})
class PersonServiceImplTest {

    @MockBean
    PersonRepository personRepository;

    @Autowired
    PersonService personService;


    // Testing with Mockito, verifying number of calls
    @Test
    public void when_savePerson_thenRepositorySaveOnce() {
        when(personRepository.save(any())).thenReturn(1);
        personService.save(new PersonDto("John", "Doe"));
        verify(personRepository, times(1)).save(any());
    }

}
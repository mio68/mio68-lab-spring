package mio68.lab.spring.test.component.service;

import mio68.lab.spring.test.component.dao.PersonDto;
import mio68.lab.spring.test.component.entity.PersonEntity;
import mio68.lab.spring.test.component.repository.PersonRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void save(PersonDto person) {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setFirstName(person.getFirstName());
        personEntity.setLastName(person.getLastName());
        personRepository.save(personEntity);
    }

}

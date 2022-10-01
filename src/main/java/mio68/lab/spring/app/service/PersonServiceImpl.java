package mio68.lab.spring.app.service;

import mio68.lab.spring.app.map.PersonMapper;
import mio68.lab.spring.app.model.Person;
import mio68.lab.spring.app.repository.PersonRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public PersonServiceImpl(
            PersonRepository personRepository,
            PersonMapper personMapper) {

        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    @Override
    public void save(Person person) {
//        personRepository.save(personMapper.mapPerson(person));
    }

}

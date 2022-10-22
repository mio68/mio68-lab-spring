package mio68.lab.spring.rest.errorhandling.service;

import mio68.lab.spring.rest.errorhandling.dto.PersonDto;
import mio68.lab.spring.rest.errorhandling.exception.PersonAlreadyExistException;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonDto johnDoe = new PersonDto("John", "Doe");

    @Override
    public void save(PersonDto person) throws PersonAlreadyExistException {
        if (johnDoe.equals(person)) {
            throw new PersonAlreadyExistException("John Doe is already exist!");
        } else {
            throw new IllegalArgumentException("John Doe is only supported!");
        }
    }

}

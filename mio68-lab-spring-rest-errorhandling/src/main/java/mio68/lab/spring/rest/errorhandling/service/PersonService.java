package mio68.lab.spring.rest.errorhandling.service;


import mio68.lab.spring.rest.errorhandling.dto.PersonDto;
import mio68.lab.spring.rest.errorhandling.exception.PersonAlreadyExistException;

public interface PersonService {

    void save(PersonDto person) throws PersonAlreadyExistException;
}

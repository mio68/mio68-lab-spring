package mio68.lab.spring.webapp.errorhandlingapp.service;

import mio68.lab.spring.webapp.common.dto.PersonDto;
import mio68.lab.spring.webapp.errorhandlingapp.exeception.PersonAlreadyExistException;
import org.springframework.stereotype.Service;

@Service
public class ExceptionalPersonServiceImpl implements ExceptionalPersonService {

    @Override
    public void save(PersonDto person) throws PersonAlreadyExistException {
        if ("John".equals(person.getFirstName()) && "Doe".equals(person.getLastName())) {
            throw new PersonAlreadyExistException("John Doe is already exist!");
        } else {
            throw new IllegalArgumentException("John Doe is only supported!");
        }
    }

}

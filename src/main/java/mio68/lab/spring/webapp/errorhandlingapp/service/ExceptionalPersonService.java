package mio68.lab.spring.webapp.errorhandlingapp.service;

import mio68.lab.spring.webapp.common.dto.PersonDto;
import mio68.lab.spring.webapp.errorhandlingapp.exeception.PersonAlreadyExistException;

public interface ExceptionalPersonService {

    void save(PersonDto person) throws PersonAlreadyExistException;
}

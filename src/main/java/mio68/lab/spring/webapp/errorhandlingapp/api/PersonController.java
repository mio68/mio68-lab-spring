package mio68.lab.spring.webapp.errorhandlingapp.api;

import mio68.lab.spring.webapp.common.dto.PersonDto;
import mio68.lab.spring.webapp.common.model.Person;
import mio68.lab.spring.webapp.errorhandlingapp.exeception.PersonAlreadyExistException;
import mio68.lab.spring.webapp.errorhandlingapp.service.ExceptionalPersonService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class PersonController {

    private final ExceptionalPersonService exceptionalPersonService;

    public PersonController(ExceptionalPersonService exceptionalPersonService) {
        this.exceptionalPersonService = exceptionalPersonService;
    }

    /**
     * Pay attention - controller method throws exception.
     * @param personDto
     * @throws PersonAlreadyExistException
     */
    @PostMapping("/person")
    @ResponseStatus(HttpStatus.CREATED)
    public void savePerson(@RequestBody @Valid PersonDto personDto)
            throws PersonAlreadyExistException {

        exceptionalPersonService.save(personDto);
    }

}

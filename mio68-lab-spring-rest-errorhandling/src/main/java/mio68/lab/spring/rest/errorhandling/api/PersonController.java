package mio68.lab.spring.rest.errorhandling.api;

import mio68.lab.spring.rest.errorhandling.dto.PersonDto;
import mio68.lab.spring.rest.errorhandling.exception.PersonAlreadyExistException;
import mio68.lab.spring.rest.errorhandling.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Pay attention - controller method throws exception.
     * @param personDto person to save
     * @throws PersonAlreadyExistException if person is already present
     */
    @PostMapping("/person")
    @ResponseStatus(HttpStatus.CREATED)
    public void savePerson(@RequestBody @Valid PersonDto personDto)
            throws PersonAlreadyExistException {

        personService.save(personDto);
    }

}

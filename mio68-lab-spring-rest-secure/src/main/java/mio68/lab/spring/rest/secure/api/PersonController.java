package mio68.lab.spring.rest.secure.api;

import mio68.lab.spring.rest.secure.dto.PersonDto;
import mio68.lab.spring.rest.secure.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/person")
    @ResponseStatus(HttpStatus.CREATED)
    public void savePerson(@RequestBody PersonDto personDto) {
        personService.save(personDto);
    }

}

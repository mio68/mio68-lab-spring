package mio68.lab.spring.webapp.secureapp.api;

import mio68.lab.spring.webapp.common.dto.PersonDto;
import mio68.lab.spring.webapp.common.map.PersonMapper;
import mio68.lab.spring.webapp.common.model.Person;
import mio68.lab.spring.webapp.common.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PersonController {

    private final PersonService personService;
    private final PersonMapper personMapper;

    public PersonController(
            PersonService personService,
            PersonMapper personMapper) {

        this.personService = personService;
        this.personMapper = personMapper;
    }

    @PostMapping("/person")
    @ResponseStatus(HttpStatus.CREATED)
    public void savePerson(@RequestBody @Valid PersonDto personDto) {
        Person person = personMapper.mapPersonDao(personDto);
        personService.save(person);
    }


    @GetMapping("/persons")
    public List<Person> list() {
        return personService.list();
    }
}

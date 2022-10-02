package mio68.lab.spring.app.api;

import mio68.lab.spring.app.dao.PersonDao;
import mio68.lab.spring.app.map.PersonMapper;
import mio68.lab.spring.app.model.Person;
import mio68.lab.spring.app.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/vi")
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
    public void savePerson(@RequestBody @Valid PersonDao personDao) {
        Person person = personMapper.mapPersonDao(personDao);
        personService.save(person);
    }


    @GetMapping("/persons")
    public List<Person> list() {
        return personService.list();
    }
}

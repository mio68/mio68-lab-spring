package mio68.lab.spring.app.map;

import mio68.lab.spring.app.dto.PersonDto;
import mio68.lab.spring.app.entity.PersonEntity;
import mio68.lab.spring.app.model.Person;

public interface PersonMapper {

    Person mapPersonDao(PersonDto personDto);

    PersonEntity mapPerson(Person person);

    Person mapPersonEntity(PersonEntity personEntity);
}

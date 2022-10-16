package mio68.lab.spring.webapp.common.map;

import mio68.lab.spring.webapp.common.dto.PersonDto;
import mio68.lab.spring.webapp.common.entity.PersonEntity;
import mio68.lab.spring.webapp.common.model.Person;

public interface PersonMapper {

    Person mapPersonDao(PersonDto personDto);

    PersonEntity mapPerson(Person person);

    Person mapPersonEntity(PersonEntity personEntity);
}

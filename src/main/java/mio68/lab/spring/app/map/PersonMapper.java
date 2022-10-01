package mio68.lab.spring.app.map;

import mio68.lab.spring.app.dao.PersonDao;
import mio68.lab.spring.app.entity.PersonEntity;
import mio68.lab.spring.app.model.Person;

public interface PersonMapper {

    Person mapPersonDao(PersonDao personDao);

    PersonEntity mapPerson(Person person);

}

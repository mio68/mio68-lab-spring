package mio68.lab.spring.app.map;

import mio68.lab.spring.app.dao.PersonDao;
import mio68.lab.spring.app.entity.PersonEntity;
import mio68.lab.spring.app.model.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonMapperImpl implements PersonMapper{

    @Override
    public Person mapPersonDao(PersonDao personDao) {
        return new Person(personDao.getFirstName(),
                personDao.getLastName());
    }

    @Override
    public PersonEntity mapPerson(Person person) {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setFirstName(person.getFirstName());
        personEntity.setLastName(person.getLastName());
        return personEntity;
    }

    @Override
    public Person mapPersonEntity(PersonEntity personEntity) {
        return new Person(
                personEntity.getFirstName(),
                personEntity.getLastName());
    }
}

package mio68.lab.spring.webapp.common.map;

import mio68.lab.spring.webapp.common.dto.PersonDto;
import mio68.lab.spring.webapp.common.entity.PersonEntity;
import mio68.lab.spring.webapp.common.model.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonMapperImpl implements PersonMapper{

    @Override
    public Person mapPersonDao(PersonDto personDto) {
        return new Person(personDto.getFirstName(),
                personDto.getLastName());
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

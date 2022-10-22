package mio68.lab.spring.test.component.repository;


import mio68.lab.spring.test.component.entity.PersonEntity;

public interface PersonRepository {

    int save(PersonEntity personEntity);

}

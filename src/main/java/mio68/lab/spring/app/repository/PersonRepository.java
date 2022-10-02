package mio68.lab.spring.app.repository;

import mio68.lab.spring.app.entity.PersonEntity;

import java.util.List;

public interface PersonRepository {

    int save(PersonEntity personEntity);

    List<PersonEntity> list();
}

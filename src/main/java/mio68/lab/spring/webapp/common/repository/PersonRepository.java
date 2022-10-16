package mio68.lab.spring.webapp.common.repository;

import mio68.lab.spring.webapp.common.entity.PersonEntity;

import java.util.List;

public interface PersonRepository {

    int save(PersonEntity personEntity);

    List<PersonEntity> list();
}

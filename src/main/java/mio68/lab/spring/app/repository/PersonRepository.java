package mio68.lab.spring.app.repository;

import mio68.lab.spring.app.entity.PersonEntity;

public interface PersonRepository {

    int save(PersonEntity personEntity);

}

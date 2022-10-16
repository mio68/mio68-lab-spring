package mio68.lab.spring.webapp.common.service;

import mio68.lab.spring.webapp.common.model.Person;

import java.util.List;

public interface PersonService {

    void save(Person person);

    List<Person> list();
}

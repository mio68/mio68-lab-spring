package mio68.lab.spring.app.service;

import mio68.lab.spring.app.model.Person;

import java.util.List;

public interface PersonService {

    void save(Person person);

    List<Person> list();
}

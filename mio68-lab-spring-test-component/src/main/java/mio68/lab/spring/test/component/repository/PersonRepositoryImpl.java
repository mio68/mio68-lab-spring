package mio68.lab.spring.test.component.repository;


import mio68.lab.spring.test.component.entity.PersonEntity;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private final AtomicInteger idGenerator = new AtomicInteger(0);
    private final ConcurrentHashMap<Integer, PersonEntity> personsById = new ConcurrentHashMap<>();

    @Override
    public int save(PersonEntity person) {
        int id = idGenerator.incrementAndGet();
        PersonEntity savedPerson = new PersonEntity(
                person.getId(),
                person.getFirstName(),
                person.getLastName());
        personsById.put(id, savedPerson);
        return id;
    }

}

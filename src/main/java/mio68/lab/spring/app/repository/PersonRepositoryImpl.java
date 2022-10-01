package mio68.lab.spring.app.repository;

import mio68.lab.spring.app.entity.PersonEntity;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private final AtomicInteger idGenerator = new AtomicInteger(0);
    private final ConcurrentHashMap<Integer, PersonEntity> personsById = new ConcurrentHashMap<>();

    public PersonRepositoryImpl() {
    }

    @Override
    public int save(PersonEntity personEntity) {
        int id = idGenerator.incrementAndGet();
        personsById.put(id, personEntity);
        return id;
    }
}

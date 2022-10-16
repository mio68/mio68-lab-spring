package mio68.lab.spring.webapp.common.repository;

import mio68.lab.spring.webapp.common.entity.PersonEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
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

    @Override
    public List<PersonEntity> list() {
        return new ArrayList<>(personsById.values());
    }
}

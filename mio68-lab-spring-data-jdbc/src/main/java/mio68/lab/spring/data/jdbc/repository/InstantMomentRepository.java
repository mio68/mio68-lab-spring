package mio68.lab.spring.data.jdbc.repository;

import mio68.lab.spring.data.jdbc.entity.InstantMoment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstantMomentRepository extends CrudRepository<InstantMoment, Long> {
}

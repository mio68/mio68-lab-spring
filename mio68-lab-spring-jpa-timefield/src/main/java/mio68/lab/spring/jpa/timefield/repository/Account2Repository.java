package mio68.lab.spring.jpa.timefield.repository;

import mio68.lab.spring.jpa.timefield.entity.Account2;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Account2Repository extends CrudRepository<Account2, Long> {

}

package mio68.lab.spring.jpa.timefield.repository;

import mio68.lab.spring.jpa.timefield.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}

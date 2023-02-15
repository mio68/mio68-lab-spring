package mio68.lab.spring.jpa.basics.repository;

import mio68.lab.spring.jpa.basics.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>,
        CustomCustomerRepository  {

}

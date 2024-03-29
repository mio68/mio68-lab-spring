package mio68.lab.spring.jpa.basics.repository;

import mio68.lab.spring.jpa.basics.entity.Customer;

import javax.transaction.Transactional;

public interface CustomCustomerRepository {

    boolean contextContains(Customer customer);

    Customer find(long id);

    void testContextContainsEntity();

    @Transactional
    Customer[] getByFindAndGetByQuery();

    void persist(Customer customer);
}

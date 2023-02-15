package mio68.lab.spring.jpa.basics.repository;

import mio68.lab.spring.jpa.basics.entity.Customer;

public interface CustomCustomerRepository {

    boolean contextContains(Customer customer);

    Customer find(long id);

    void testContextContainsEntity();
}

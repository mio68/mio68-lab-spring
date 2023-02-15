package mio68.lab.spring.jpa.basics.repository;

import lombok.extern.slf4j.Slf4j;
import mio68.lab.spring.jpa.basics.entity.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Slf4j
public class DemoCustomerRepo {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void transactionalTestContextContainsJustFoundEntity() {
        log.info("transactionalTestContextContainsJustFoundEntity");
        Customer customer = entityManager.find(Customer.class, 1L);
        log.info("customer: " + customer);
        boolean contextContainsIt = entityManager.contains(customer);
        log.info("entityManager.contains(customer): " + contextContainsIt); //true
    }

    @Transactional
    public void transactionalTestContextContainsEntityWithExistingDbRow() {
        log.info("transactionalTestContextContainsEntityWithExistingDbRow");
        Customer customer = new Customer();
        customer.setId(1L);
        log.info("customer: " + customer);
        boolean contextContainsIt = entityManager.contains(customer);
        log.info("entityManager.contains(customer): " + contextContainsIt); //false
    }

    public void nonTransactionalTestContextContainsJustFoundEntity() {
        log.info("nonTransactionalTestContextContainsJustFoundEntity");
        Customer customer = entityManager.find(Customer.class, 1L);
        log.info("customer: " + customer);
        boolean contextContainsIt = entityManager.contains(customer);
        log.info("entityManager.contains(customer): " + contextContainsIt); // false
    }


}

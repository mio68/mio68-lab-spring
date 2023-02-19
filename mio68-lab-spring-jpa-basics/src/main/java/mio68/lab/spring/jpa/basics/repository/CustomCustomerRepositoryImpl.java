package mio68.lab.spring.jpa.basics.repository;

import lombok.extern.slf4j.Slf4j;
import mio68.lab.spring.jpa.basics.entity.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Slf4j
public class CustomCustomerRepositoryImpl implements CustomCustomerRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public CustomCustomerRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean contextContains(Customer customer) {
        log.info("entityManager.isJoinedToTransaction(): " + entityManager.isJoinedToTransaction());
        return entityManager.contains(customer);
    }

    @Override
    public Customer find(long id) {
        log.info("entityManager.isJoinedToTransaction(): " + entityManager.isJoinedToTransaction());
        return entityManager.find(Customer.class, id);
    }

    @Override
    @Transactional
    public void testContextContainsEntity() {
        Customer customer = entityManager.find(Customer.class, 1L);
        log.info("customer: " + customer);
        boolean contextContainsIt = entityManager.contains(customer);
        log.info("entityManager.contains(customer): " + contextContainsIt); // true
    }

}

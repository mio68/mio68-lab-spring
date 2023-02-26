package mio68.lab.spring.jpa.basics.repository;

import lombok.extern.slf4j.Slf4j;
import mio68.lab.spring.jpa.basics.entity.Customer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Repository
@Slf4j
public class CustomCustomerRepositoryImpl implements CustomCustomerRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    private final PlatformTransactionManager platformTransactionManager;

    public CustomCustomerRepositoryImpl(
            EntityManager entityManager,
            PlatformTransactionManager platformTransactionManager) {

        this.entityManager = entityManager;
        this.platformTransactionManager = platformTransactionManager;
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

    @Override
    @Transactional
    public Customer[] getByFindAndGetByQuery() {
        Customer customerFound = entityManager.find(Customer.class, 1L);

        // Imitate update customer by another transaction
        Customer updatedByOtherThread = updateCustomerWithId1Async();

        Customer customerSelectedByQuery = entityManager.createQuery("""
                                SELECT c 
                                FROM Customer c
                                WHERE c.id = 1 
                                """,
                        Customer.class)
                .getSingleResult();

//        entityManager.refresh(customerSelectedByQuery);

        return new Customer[]{customerFound, customerSelectedByQuery, updatedByOtherThread};
    }

    private Customer updateCustomerWithId1Async() {
        return CompletableFuture.supplyAsync(() -> {
            TransactionTemplate transactionTemplate =
                    new TransactionTemplate(platformTransactionManager);
            return transactionTemplate.execute((status) -> {
                Customer customer = entityManager.find(Customer.class, 1L);
                customer.setName("Updated at:" + LocalDateTime.now());
                return customer;
            });
        }).join();
    }

}

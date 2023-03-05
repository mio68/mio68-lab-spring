package mio68.lab.spring.jpa.basics.repository;

import mio68.lab.spring.jpa.basics.JpaBasicsDemoApplication;
import mio68.lab.spring.jpa.basics.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CustomCustomerRepositoryImplTest {

    @Autowired
    CustomCustomerRepositoryImpl customerRepository;

    @Autowired
    PlatformTransactionManager platformTransactionManager;

    @Test
    public void foundAndSelectedByQueryCustomersAreIdentical() {
        Customer[] customers = customerRepository.getByFindAndGetByQuery();
        Customer customerFound = customers[0];
        System.out.println("found by id: " + customerFound);
        Customer customerSelectedByQuery = customers[1];
        System.out.println("selected by query: " + customerSelectedByQuery);
        Customer updatedByOtherThread = customers[2];
        System.out.println("updated by other thread: " + updatedByOtherThread);
        assertTrue(customerFound == customerSelectedByQuery,
                "entities are not identical");
        assertTrue(customerFound.equals(customerSelectedByQuery),
                "entities are not equal");

        assertTrue(customerFound != updatedByOtherThread,
                "entities are identical");
        assertTrue(!customerFound.equals(updatedByOtherThread),
                "entities are equal");

    }

    @Test
    public void whenSaved_thenFound() {
        Customer customer = new Customer();
        customer.setName("customer A");

        TransactionTemplate transactionTemplate = new TransactionTemplate(platformTransactionManager);

        transactionTemplate.executeWithoutResult(
                status -> customerRepository.persist(customer)
        );

        System.out.println(customer);

        Customer customer1 = customerRepository.find(customer.getId());

        System.out.println(customer1);
    }


}
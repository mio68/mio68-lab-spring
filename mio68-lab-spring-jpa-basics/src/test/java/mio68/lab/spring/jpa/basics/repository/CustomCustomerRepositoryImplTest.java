package mio68.lab.spring.jpa.basics.repository;

import mio68.lab.spring.jpa.basics.JpaBasicsDemoApplication;
import mio68.lab.spring.jpa.basics.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {JpaBasicsDemoApplication.class})
class CustomCustomerRepositoryImplTest {

    @Autowired
    CustomCustomerRepositoryImpl customerRepository;

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

}
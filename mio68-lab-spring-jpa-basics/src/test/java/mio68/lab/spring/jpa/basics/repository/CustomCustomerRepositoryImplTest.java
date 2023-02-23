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
        System.out.println(customerFound);
        Customer customerSelectedByQuery = customers[1];
        assertTrue(customerFound == customerSelectedByQuery,
                "entities are not identical");
        assertTrue(customerFound.equals(customerSelectedByQuery),
                "entities are not equal");
    }

}
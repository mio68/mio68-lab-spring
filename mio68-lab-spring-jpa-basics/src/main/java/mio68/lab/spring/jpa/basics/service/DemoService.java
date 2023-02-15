package mio68.lab.spring.jpa.basics.service;

import lombok.extern.slf4j.Slf4j;
import mio68.lab.spring.jpa.basics.entity.Customer;
import mio68.lab.spring.jpa.basics.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Slf4j
public class DemoService {

    private final CustomerRepository customerRepository;

    public DemoService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public void testContextContainsEntityWithId1() {
        Customer firstCustomerFound = customerRepository.find(1L);
        log.info("customer: " + firstCustomerFound);
        boolean contextContainsFirstCustomer = customerRepository.contextContains(firstCustomerFound);
        log.info("customerRepository.contextContains(firstCustomerFound): " + contextContainsFirstCustomer);
    }

    // With @Transactional annotation this method updates entity quite well
    // Without annotation it just updates entity name property but doesn't flush it back to DB
    @Transactional
    public void updateEntityWithId1() {
        Customer firstCustomerFound = customerRepository.find(1L);
        log.info("customer: " + firstCustomerFound);
        firstCustomerFound.setName(
                String.format(
                        "%s updated at: %s",
                        "John Doe",
                        LocalDateTime.now()));
    }

}

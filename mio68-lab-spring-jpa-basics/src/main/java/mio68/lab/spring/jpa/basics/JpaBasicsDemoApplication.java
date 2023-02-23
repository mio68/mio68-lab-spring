package mio68.lab.spring.jpa.basics;

import lombok.extern.slf4j.Slf4j;
import mio68.lab.spring.jpa.basics.entity.Customer;
import mio68.lab.spring.jpa.basics.repository.CustomerRepository;
import mio68.lab.spring.jpa.basics.repository.DemoCustomerRepo;
import mio68.lab.spring.jpa.basics.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.transaction.Transactional;

@SpringBootApplication
@EnableTransactionManagement
@Slf4j
public class JpaBasicsDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(JpaBasicsDemoApplication.class, args);
    }

//    @Component
//    public static class DemoApplicationRunner implements ApplicationRunner {
//
//        @Autowired
//        CustomerRepository customerRepository;
//
//        @Autowired
//        DemoCustomerRepo demoCustomerRepo;
//
//        @Autowired
//        DemoService demoService;
//
//        @Override
//        public void run(ApplicationArguments args) {
//
//            if (customerRepository.findById(1L).isEmpty()) {
//                Customer customer = new Customer();
//                customer.setId(1L);
//                customer.setName("John Doe");
//                customer.setEmail("john_doe@mycompany.com");
//                customerRepository.save(customer);
//            }
//
//            // @Transactional annotation won't take effect on this call,
//            // because it's a direct call of a method within same class
//            testContextContainsEntityWithId1(); //contains returns false
//
//            customerRepository.testContextContainsEntity(); //contains returns true
//
//            demoCustomerRepo.transactionalTestContextContainsJustFoundEntity(); //contains returns true
//            demoCustomerRepo.nonTransactionalTestContextContainsJustFoundEntity(); //contains returns false
//            demoCustomerRepo.transactionalTestContextContainsEntityWithExistingDbRow(); //contains returns false
//
//            demoService.updateEntityWithId1();
//            demoService.testContextContainsEntityWithId1(); //contains returns true
//
//        }
//
//        @Transactional
//        public void testContextContainsEntityWithId1() {
//            Customer firstCustomerFound = customerRepository.find(1L);
//            log.info("customer: " + firstCustomerFound);
//            boolean contextContainsFirstCustomer = customerRepository.contextContains(firstCustomerFound);
//            log.info("customerRepository.contextContains(firstCustomerFound): " + contextContainsFirstCustomer);
//        }
//    }
}
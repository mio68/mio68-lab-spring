package mio68.lab.spring.jdbc.manyds.configuration;

import mio68.lab.spring.jdbc.manyds.service.CustomerService;
import mio68.lab.spring.jdbc.manyds.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
public class ServiceConfiguration {

    @Bean
    public CustomerService mainCustomerService(
            @Qualifier("mainJdbcTemplate")  JdbcTemplate jdbcTemplate,
            @Qualifier("mainTransactionTemplate") TransactionTemplate transactionTemplate) {

        return new CustomerServiceImpl(jdbcTemplate, transactionTemplate);
    }

    @Bean
    public CustomerService standinCustomerService(
            @Qualifier("standinJdbcTemplate")  JdbcTemplate jdbcTemplate,
            @Qualifier("standinTransactionTemplate") TransactionTemplate transactionTemplate) {

        return new CustomerServiceImpl(jdbcTemplate, transactionTemplate);
    }

}

package mio68.lab.spring.jdbc.manyds.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

public class CustomerServiceImpl implements CustomerService {

    private final JdbcTemplate jdbcTemplate;
    private final TransactionTemplate transactionTemplate;

    public CustomerServiceImpl(
            JdbcTemplate jdbcTemplate,
            TransactionTemplate transactionTemplate) {

        this.jdbcTemplate = jdbcTemplate;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public void addTwoCustomers(int id1, String name1, int id2, String name2) {
        transactionTemplate.executeWithoutResult(
                (status) -> {
                    jdbcTemplate.update("INSERT INTO customer(id, name) values(?, ?)", id1, name1);
                    jdbcTemplate.update("INSERT INTO customer(id, name) values(?, ?)", id2, name2);
                }
        );
    }

}

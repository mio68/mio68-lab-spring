package mio68.lab.spring.jdbc.manyds;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import mio68.lab.spring.jdbc.manyds.service.CustomerService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@SpringBootApplication
@EnableTransactionManagement
@Slf4j
public class ManyDsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManyDsApplication.class, args);
    }

    @Component
    public static class ManyDsApplicationDemo implements ApplicationRunner {

        private final DataSource mainDataSource;
        private final DataSource standinDataSource;
        private final JdbcTemplate mainJdbcTemplate;
        private final JdbcTemplate standinJdbcTemplate;
        private final CustomerService mainCustomerService;
        private final CustomerService standinCustomerService;

        public ManyDsApplicationDemo(
                DataSource mainDataSource,
                DataSource standinDataSource,
                JdbcTemplate mainJdbcTemplate,
                JdbcTemplate standinJdbcTemplate,
                CustomerService mainCustomerService,
                CustomerService standinCustomerService) {

            this.mainDataSource = mainDataSource;
            this.standinDataSource = standinDataSource;
            this.mainJdbcTemplate = mainJdbcTemplate;
            this.standinJdbcTemplate = standinJdbcTemplate;
            this.mainCustomerService = mainCustomerService;
            this.standinCustomerService = standinCustomerService;
        }

        @Override
        public void run(ApplicationArguments args) {
            logDataSourcesInfo();
            intiDb();
            insertCustomers();
        }

        private void insertCustomers() {
            mainCustomerService.addTwoCustomers(10, "Alice5", 11, "Bob5");
            standinCustomerService.addTwoCustomers(10, "Doc5", 11, "Marty5");
        }

        private void intiDb() {
            mainJdbcTemplate.execute("""
                    CREATE TABLE IF NOT EXISTS customer (
                     id int PRIMARY KEY,
                     name varchar(100),
                     email varchar(100))
                    """);

            standinJdbcTemplate.execute("""
                    CREATE TABLE IF NOT EXISTS customer (
                     id int PRIMARY KEY,
                     name varchar(100),
                     email varchar(100))
                    """);
        }

        private void logDataSourcesInfo() {
            log.info("main data source class: " + mainDataSource.getClass().getSimpleName());
            log.info("main data source: " + mainDataSource);
            if (mainDataSource.getClass().equals(HikariDataSource.class)) {
                log.info("HikariDataSource maximum-pool-size:" + ((HikariDataSource) mainDataSource).getMaximumPoolSize());
            }
            log.info("standin data source class: " + standinDataSource.getClass().getSimpleName());
            log.info("standin data source: " + standinDataSource);
            if (standinDataSource.getClass().equals(HikariDataSource.class)) {
                log.info("HikariDataSource maximum-pool-size:" + ((HikariDataSource) standinDataSource).getMaximumPoolSize());
            }
        }

    }
}

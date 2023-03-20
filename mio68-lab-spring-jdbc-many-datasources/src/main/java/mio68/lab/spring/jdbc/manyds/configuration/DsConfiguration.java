package mio68.lab.spring.jdbc.manyds.configuration;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

@Configuration
public class DsConfiguration {

    @Bean
    @ConfigurationProperties("spring.datasource.main")
    public DataSourceProperties mainDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.standin")
    public DataSourceProperties standinDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.main.hikari")
    public DataSource mainDataSource() {
        return mainDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.standin.hikari")
    public DataSource standinDataSource() {
        return standinDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public JdbcTemplate mainJdbcTemplate() {
        return new JdbcTemplate(mainDataSource());
    }

    @Bean
    public JdbcTemplate standinJdbcTemplate() {
        return new JdbcTemplate(standinDataSource());
    }

    @Bean
    public PlatformTransactionManager mainTransactionManager() {
        return new DataSourceTransactionManager(mainDataSource());
    }

    @Bean
    public PlatformTransactionManager standinTransactionManager() {
        return new DataSourceTransactionManager(standinDataSource());
    }

    @Bean
    public TransactionTemplate mainTransactionTemplate() {
        return new TransactionTemplate(mainTransactionManager());
    }

    @Bean
    public TransactionTemplate standinTransactionTemplate() {
        return new TransactionTemplate(standinTransactionManager());
    }

}

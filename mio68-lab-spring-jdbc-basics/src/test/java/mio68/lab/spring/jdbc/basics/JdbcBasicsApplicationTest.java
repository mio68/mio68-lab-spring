package mio68.lab.spring.jdbc.basics;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest
class JdbcBasicsApplicationTest {

    @Autowired
    DataSource dataSourceByType;

    @Autowired
    @Qualifier("dataSource")
    DataSource dataSourceByName;

    @Test
    public void justStartTest() {
        Assertions.assertSame(dataSourceByType, dataSourceByName, "DataSource by type are not identical to DataSource by name 'dataSource'");
    }

}
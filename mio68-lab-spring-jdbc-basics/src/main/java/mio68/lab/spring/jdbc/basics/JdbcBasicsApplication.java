package mio68.lab.spring.jdbc.basics;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@SpringBootApplication
public class JdbcBasicsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JdbcBasicsApplication.class, args);
    }

    @Component
    static class JdbcBasicsApplicationDemo implements ApplicationRunner {

        private final DataSource dataSourceByType;
        private final DataSource dataSourceByName;

        JdbcBasicsApplicationDemo(
                DataSource dataSourceByType,
                @Qualifier("dataSource")  DataSource dataSourceByName) {
            this.dataSourceByType = dataSourceByType;
            this.dataSourceByName = dataSourceByName;
        }

        @Override
        public void run(ApplicationArguments args) {
            System.out.println("Injected by DataSource type.");
            System.out.println("Data source: " + dataSourceByType);
            System.out.println("Data source class: " + dataSourceByType.getClass());

            System.out.println("\nInjected by dataSource name.");
            System.out.println("Data source: " + dataSourceByName);
            System.out.println("Data source class: " + dataSourceByName.getClass());
        }
    }

}

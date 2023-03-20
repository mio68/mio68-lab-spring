package mio68.lab.spring.embedded.postgresql;

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;

@SpringBootApplication
@Slf4j
public class EmbeddedPostgresqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmbeddedPostgresqlApplication.class, args);
    }

    @Bean
    public DataSource dataSource() throws IOException {
        EmbeddedPostgres embeddedPostgres = EmbeddedPostgres.builder().start();
        log.info("jdbc url: [{}]", embeddedPostgres.getJdbcUrl("sa","testdb"));
        return embeddedPostgres.getPostgresDatabase();
    }

    @Component
    static class EmbeddedDbDemo implements ApplicationRunner {

        private final JdbcTemplate jdbcTemplate;

        EmbeddedDbDemo(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }

        @Override
        public void run(ApplicationArguments args) {
            jdbcTemplate.execute("""
                    CREATE TABLE customer (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        email VARCHAR(255) NOT NULL,
                        UNIQUE(name))
                    """);

            jdbcTemplate.update("""
                    INSERT INTO customer (name, email) VALUES
                    ('Marten Deinum', 'marten.deinum@conspect.nl'),
                    ('Josh Long', 'jlong@pivotal.com'),
                    ('John Doe', 'john.doe@island.io'),
                    ('Jane Doe', 'jane.doe@island.io')
                    """);
        }
    }

}

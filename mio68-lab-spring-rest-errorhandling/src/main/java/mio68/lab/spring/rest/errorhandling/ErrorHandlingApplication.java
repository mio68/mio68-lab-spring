package mio68.lab.spring.rest.errorhandling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * How to handle exception that controllers throw?
 * @link https://www.baeldung.com/exception-handling-for-rest-with-spring
 *
 *
 * How to disable any security for web app read:
 * https://www.baeldung.com/spring-security-disable-profile
 */
@SpringBootApplication
public class ErrorHandlingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErrorHandlingApplication.class, args);
    }

}

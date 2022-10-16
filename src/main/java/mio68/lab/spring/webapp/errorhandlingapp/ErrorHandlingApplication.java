package mio68.lab.spring.webapp.errorhandlingapp;

import mio68.lab.spring.webapp.common.map.PersonMapper;
import mio68.lab.spring.webapp.common.map.PersonMapperImpl;
import mio68.lab.spring.webapp.errorhandlingapp.configuration.ApplicationNoSecurity;
import mio68.lab.spring.webapp.errorhandlingapp.handler.Handler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

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

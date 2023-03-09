package mio68.lab.spring.confprop.validation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ConfPropValidationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfPropValidationApplication.class, args);
    }

}

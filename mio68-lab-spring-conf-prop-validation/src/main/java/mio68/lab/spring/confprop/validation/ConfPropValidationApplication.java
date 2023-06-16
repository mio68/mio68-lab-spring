package mio68.lab.spring.confprop.validation;

import lombok.RequiredArgsConstructor;
import mio68.lab.spring.confprop.validation.properties.AppProperties;
import mio68.lab.spring.confprop.validation.properties.OtherProperties;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ConfigurationPropertiesScan
// Other version loads specified properties
// @EnableConfigurationProperties({OtherProperties.class, AppProperties.class})
public class ConfPropValidationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfPropValidationApplication.class, args);
    }

    @Component
    @RequiredArgsConstructor
    public static class ConfigurationPrinter implements ApplicationRunner {
        private final OtherProperties otherProperties;
        private final AppProperties appProperties;

        @Override
        public void run(ApplicationArguments args) {
            System.out.println(appProperties);
            System.out.println(otherProperties);
        }
    }

}

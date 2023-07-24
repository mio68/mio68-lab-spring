package mio68.lab.spring.confprop.yaml;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@Slf4j
public class ConfPropYamlApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfPropYamlApplication.class, args);
    }

    @Component
    static class ConfPropYamlDemo implements ApplicationRunner {

        private final Map<String, Object> consumerProperties;
        private final BeanA beanA;

        ConfPropYamlDemo(
                @Qualifier("consumerProperties") Map<String, Object> consumerProperties,
                BeanA beanA) {
            this.consumerProperties = consumerProperties;
            this.beanA = beanA;
        }


        @Override
        public void run(ApplicationArguments args) {
            log.info("consumerProperties: {}", consumerProperties);
            log.info("beanA: {}", beanA);
        }
    }

    @Bean
    @ConfigurationProperties("kafka.consumer")
    public Map<String, Object> consumerProperties() {
        return new HashMap<>();
    }

    @Bean
    @ConfigurationProperties("beans.bean-a")
    public BeanA beanA() {
        return new BeanA();
    }

    @Data
    static class BeanA {
        private int id;
        private String name;
    }

}

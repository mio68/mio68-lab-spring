package mio68.lab.spring.kafka;

import lombok.extern.slf4j.Slf4j;
import mio68.lab.spring.kafka.properties.KafkaProperties;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ConfigurationPropertiesScan
@Slf4j
public class KafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class, args);
    }


    @Component
    static class KafkaApplicationDemo implements ApplicationRunner {

        private final KafkaProperties kafkaProperties;
        private final ContainerProperties containerProperties;

        KafkaApplicationDemo(
                KafkaProperties kafkaProperties,
                ContainerProperties containerProperties) {

            this.kafkaProperties = kafkaProperties;
            this.containerProperties = containerProperties;
        }

        @Override
        public void run(ApplicationArguments args) throws Exception {
            log.info("clientProperties: {}",kafkaProperties.getClientProperties());
//            log.info("containerProperties: {}",containerProperties);
        }
    }

}

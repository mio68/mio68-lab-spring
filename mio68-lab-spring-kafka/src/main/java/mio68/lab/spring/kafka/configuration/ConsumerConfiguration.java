package mio68.lab.spring.kafka.configuration;

import mio68.lab.spring.kafka.consumer.SimpleMessageListener;
import mio68.lab.spring.kafka.properties.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListenerContainer;

@Configuration
public class ConsumerConfiguration {

    @Bean
    @ConfigurationProperties("mio68.lab.spring.kafka.consumer.concurrent-message-listener-container")
    public MessageListenerContainer concurrentMessageListenerContainer(
            SimpleMessageListener simpleMessageListener,
            ContainerProperties containerProperties,
            ConsumerFactory<String, String> consumerFactory) {

        ConcurrentMessageListenerContainer<String, String> concurrentMessageListenerContainer =
                new ConcurrentMessageListenerContainer<>(consumerFactory, containerProperties);
        concurrentMessageListenerContainer.setupMessageListener(simpleMessageListener);
        return concurrentMessageListenerContainer;
    }

    @Bean
    @ConfigurationProperties("mio68.lab.spring.kafka.consumer.properties")
    public ContainerProperties containerProperties(
            KafkaProperties kafkaProperties) {

        String[] topics = kafkaProperties
                .getConsumer()
                .getTopics()
                .toArray(new String[]{});

        return new ContainerProperties(topics);
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory(KafkaProperties kafkaProperties) {
        return new DefaultKafkaConsumerFactory<>(kafkaProperties.getClientProperties());
    }

}

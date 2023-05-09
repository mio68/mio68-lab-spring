package mio68.lab.spring.kafka.configuration;

import mio68.lab.spring.kafka.consumer.SimpleMessageListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListenerContainer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class SimpleConsumerConfiguration {

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
            @Value("${mio68.lab.spring.kafka.consumer.topics}")
                    List<String> topics) {

        return new ContainerProperties(topics.toArray(String[]::new));
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(clientProperties());
    }

    @Bean
    @ConfigurationProperties("mio68.lab.spring.kafka.client-properties")
    public Map<String, Object> clientProperties() {
        return new HashMap<>();
    }

}

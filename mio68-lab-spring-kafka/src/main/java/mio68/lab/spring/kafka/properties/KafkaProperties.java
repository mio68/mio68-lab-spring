package mio68.lab.spring.kafka.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ConfigurationProperties("mio68.lab.spring.kafka")
@Data
public class KafkaProperties {

    private Map<String, Object> clientProperties = new HashMap<>();
    private ConsumerProperties consumer = new ConsumerProperties();

    @Data
    public static class ConsumerProperties {
        private List<String> topics = new ArrayList<>();
    }

}

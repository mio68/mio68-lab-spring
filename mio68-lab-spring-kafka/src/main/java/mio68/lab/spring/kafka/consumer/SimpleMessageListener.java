package mio68.lab.spring.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SimpleMessageListener implements MessageListener<String, String> {

    public static final String SLEEP = "sleep:";

    @Override
    public void onMessage(ConsumerRecord<String, String> consumerRecord) {
        log.info(
                "onMessage key: [{}] value: [{}]",
                consumerRecord.key(),
                consumerRecord.value());

        String value =  consumerRecord.value();
        if (value.startsWith(SLEEP)) {
            try {
                Thread.sleep(Integer.parseInt(value.substring(SLEEP.length())));
            } catch (InterruptedException e) {
                throw new RuntimeException("Interrupted", e);
            }
        }
    }

}

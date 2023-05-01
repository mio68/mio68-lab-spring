package mio68.lab.spring.kafka.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.kafka.event.KafkaEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaEventListener implements ApplicationListener<KafkaEvent> {

    @Override
    public void onApplicationEvent(KafkaEvent event) {
        log.info("event: {}", event);
    }

}

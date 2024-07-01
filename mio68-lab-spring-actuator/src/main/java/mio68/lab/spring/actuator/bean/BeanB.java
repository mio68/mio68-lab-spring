package mio68.lab.spring.actuator.bean;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
public class BeanB {

    @PreDestroy
    public void close() {
        log.info("Closing [{}]", this);
    }
}

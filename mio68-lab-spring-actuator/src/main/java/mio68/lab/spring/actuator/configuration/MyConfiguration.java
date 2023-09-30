package mio68.lab.spring.actuator.configuration;

import mio68.lab.spring.actuator.bean.BeanA;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfiguration {

    @Bean
    public BeanA beanA() {
        return new BeanA();
    }

    @Bean
    public BeanA anotherBeanA() {
        return new BeanA();
    }


}

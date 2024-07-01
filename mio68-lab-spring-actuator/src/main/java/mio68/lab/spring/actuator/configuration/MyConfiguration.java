package mio68.lab.spring.actuator.configuration;

import mio68.lab.spring.actuator.bean.BeanA;
import mio68.lab.spring.actuator.bean.BeanB;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

import java.util.List;

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


    @Bean
    public List<BeanB> beanBList(GenericApplicationContext context) {
        BeanB b1 = new BeanB();
       // context.getBeanFactory().initializeBean(b1, )
        context.registerBean("beanB-1", BeanB.class);
        BeanB b2 = new BeanB();
        context.registerBean("beanB-2", BeanB.class);
        BeanB b3 = new BeanB();
        context.registerBean("beanB-3", BeanB.class);


        return List.of(b1, b2, b3);
    }

    @Bean
    public BeanB classicBeanB() {
        return new BeanB();
    }

}

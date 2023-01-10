package mio68.lab.spring.jmx.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.annotation.Description;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("NullableProblems")
@Component
/* Spring boot will detect the @ManagedResource annotated beans and register
them with the JMX server.
*/
@ManagedResource(description = "This is an example of a bean to be managed with JMX")
@Slf4j
public class HelloBean implements BeanNameAware {

    /* Fields are not visible with JMX */
    private final AtomicInteger counter = new AtomicInteger(0);
    private String beanName;

    /*
     * The operation to expose with JMX
     */
    @ManagedOperation
    public void sayHello() {
        counter.incrementAndGet();
        log.info("Hello from {} bean!", beanName);
    }

    @Override
    public void setBeanName(String s) {
        beanName = s;
    }

    @ManagedOperation
    public int getCounter() {
        return counter.get();
    }

    /* This operation is not visible with JMX */
    public String getBeanName() {
        return beanName;
    }
}


package mio68.lab.spring.test;

import lombok.experimental.UtilityClass;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@UtilityClass
public class ApplicationContextHelper {
    public static void printInfo(ApplicationContext applicationContext) {
        Arrays.stream(applicationContext.getBeanDefinitionNames())
                .map(name -> String.format(
                        "%s: %s",
                        name,
                        applicationContext.getBean(name).getClass().getName()))
                .filter(beanInfo -> beanInfo.contains("mio68"))
                .forEach(System.out::println);
    }
}

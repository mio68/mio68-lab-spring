package mio68.lab.spring.debug;

import mio68.lab.spring.debug.component.TypeA;
import mio68.lab.spring.debug.component.TypeB;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DebugApplicationConfiguration {

    @Bean
    public TypeB anotherTypeB(TypeA typeA) {
        return new TypeB(typeA);
    }

}

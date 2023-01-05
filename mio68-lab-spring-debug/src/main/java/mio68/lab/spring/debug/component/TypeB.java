package mio68.lab.spring.debug.component;

import org.springframework.stereotype.Component;

@Component
public class TypeB {
    private final TypeA typeA;

    public TypeB(TypeA typeA) {
        this.typeA = typeA;
    }
}

package mio68.lab.spring.data.jdbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Embedded;

@Value
@Builder
public class Resource {

    @Id
    private final Long id;

    @Embedded(onEmpty = Embedded.OnEmpty.USE_NULL)
    private final CommonInfo commonInfo;

    private final String type;

}

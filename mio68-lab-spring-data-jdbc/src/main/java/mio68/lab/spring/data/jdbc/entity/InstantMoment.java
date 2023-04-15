package mio68.lab.spring.data.jdbc.entity;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Value
@Builder
@Table("times")
public class InstantMoment {

    @Id
    private final Long id;

    private final Instant moment;

}

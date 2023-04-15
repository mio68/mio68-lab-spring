package mio68.lab.spring.data.jdbc.entity;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@Value
@Builder
@Table("times")
public class TimestampMoment {

    @Id
    private final Long id;

    private final Timestamp moment;

}

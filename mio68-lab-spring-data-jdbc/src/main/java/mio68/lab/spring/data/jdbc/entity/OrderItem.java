package mio68.lab.spring.data.jdbc.entity;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;

@Value
@Builder(toBuilder = true)
public class OrderItem {

    @Id
    Long id;

    Long orderId;

    String name;

}

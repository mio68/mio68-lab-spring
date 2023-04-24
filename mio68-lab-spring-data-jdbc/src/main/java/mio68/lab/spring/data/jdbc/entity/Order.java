package mio68.lab.spring.data.jdbc.entity;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.HashSet;
import java.util.Set;

@Value
@Builder(toBuilder = true)
public class Order {

    @Id
    Long id;

    String title;

    // Specify name of FOREIGN KEY column in the order_item table
    @MappedCollection(idColumn = "order_id")
    @Singular
    Set<OrderItem> orderItems;

}

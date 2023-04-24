package mio68.lab.spring.data.jdbc.repository;

import mio68.lab.spring.data.jdbc.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {

}

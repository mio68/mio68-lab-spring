package mio68.lab.spring.data.jdbc;

import lombok.extern.slf4j.Slf4j;
import mio68.lab.spring.data.jdbc.entity.*;
import mio68.lab.spring.data.jdbc.repository.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootApplication
@Slf4j
public class SpringDataJdbcApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringDataJdbcApplication.class, args);
    }

    @Component
    static class SpringDataJdbcApplicationDemo implements ApplicationRunner {

        private final ResourceRepository resourceRepository;
        private final TimestampMomentRepository timestampMomentRepository;
        private final InstantMomentRepository instantMomentRepository;
        private final MyRepository myRepository;
        private final MyRepository2 myRepository2;
        private final OrderRepository orderRepository;

        SpringDataJdbcApplicationDemo(
                ResourceRepository resourceRepository,
                TimestampMomentRepository timestampMomentRepository,
                InstantMomentRepository instantMomentRepository,
                MyRepository myRepository,
                MyRepository2 myRepository2,
                OrderRepository orderRepository) {

            this.resourceRepository = resourceRepository;
            this.timestampMomentRepository = timestampMomentRepository;
            this.instantMomentRepository = instantMomentRepository;
            this.myRepository = myRepository;
            this.myRepository2 = myRepository2;
            this.orderRepository = orderRepository;
        }

        @Override
        public void run(ApplicationArguments args) throws Exception {
            updateOrder51();
//            orderRepoDemo();
//            customRepoDemo();
//            resourceDemo();
//            timestampMomentDemo();
//            instantMomentDemo();
//            tryToSaveNanos();
//            printInstantMoments();
        }

        private void orderRepoDemo() {
            Order order = Order.builder()
                    .title("order A")
                    .orderItem(
                            OrderItem.builder()
                                    .name("item 10-1")
                                    .build())
                    .orderItem(
                            OrderItem.builder()
                                    .name("item 10-2")
                                    .build())
                    .orderItem(
                            OrderItem.builder()
                                    .name("item 10-3")
                                    .build())
                    .build();

            log.info("order to save: {}", order);
            Order savedOrder = orderRepository.save(order);
            log.info("saved order: {}", savedOrder);

            Order orderFound = orderRepository.findById(savedOrder.getId()).get();
            log.info("order found by id {}: {}", savedOrder.getId(), orderFound);

            Order orderWithNoItems = Order.builder()
                    .title("without items")
                    .build();
            log.info("order with no items: {}", orderWithNoItems);
            Order orderWithNoItemsSaved = orderRepository.save(orderWithNoItems);
            log.info("order with no items saved: {}", orderWithNoItemsSaved);
            Order orderWithNoItemsFound = orderRepository.findById(orderWithNoItemsSaved.getId()).get();
            log.info("order with no items found: {}", orderWithNoItemsFound);

            // build order items set first
            Set<OrderItem> orderItems = Set.of(
                    OrderItem.builder().name("item1").build(),
                    OrderItem.builder().name("item2").build()
            );
            Order orderWithItems = Order.builder()
                    .title("order with items")
                    .orderItems(orderItems)
                    .build();
            log.info("order with items: {}", orderWithItems);
            Order orderWithItemsSaved = orderRepository.save(orderWithItems);
            log.info("order with items saved: {}", orderWithItemsSaved);

            Order orderWithItemsFound = orderRepository.findById(orderWithItemsSaved.getId()).get();
            log.info("order with items found: {}", orderWithItemsFound);

            Order orderForDeletion = orderFound;
            orderRepository.delete(orderForDeletion);
            log.info("deleted order exists: {}", orderRepository.findById(orderForDeletion.getId()).isPresent());

            updateOrder51();

        }

        private void updateOrder51() {
            // How to update an order?
            Order updateIt = orderRepository.findById(51L).get();
            Order orderWithItemsForUpdate = updateIt.toBuilder()
                    .title("order with items updated")
                    .clearOrderItems()
                    .orderItems(
                            updateIt.getOrderItems()
                                    .stream()
                                    .limit(1)
                                    .collect(Collectors.toSet()))
                    .orderItem(
                            OrderItem.builder()
                                    .name("item added")
                                    .build())
                    .build();

            log.info("order with items updated {}", orderWithItemsForUpdate);
            Order updated = orderRepository.save(orderWithItemsForUpdate);
            log.info("updated  {}", updated);
            Order updatedFound = orderRepository.findById(updated.getId()).get();
            log.info("updated found {}", updatedFound);
        }

        private void customRepoDemo() {
            Resource resource = myRepository.findById(1L).get();
            System.out.println(resource);

            resource = myRepository2.findById(2L).get();
            System.out.println(resource);
        }

        private void printInstantMoments() {
            System.out.println(instantMomentRepository.findById(1L).get());
            System.out.println(instantMomentRepository.findById(2L).get());
            System.out.println(instantMomentRepository.findById(3L).get());
            System.out.println(instantMomentRepository.findById(4L).get());
        }

        private void instantMomentDemo() {
            InstantMoment instantMoment = InstantMoment.builder()
                    .moment(Instant.now())
                    .build();
            instantMomentRepository.save(instantMoment);
        }

        private void timestampMomentDemo() {
            TimestampMoment timestampMoment = TimestampMoment.builder()
                    .moment(new Timestamp(
                            2023,
                            4,
                            13,
                            22,
                            39,
                            00,
                            123456))
                    .build();

            timestampMomentRepository.save(timestampMoment);

            timestampMoment = TimestampMoment.builder()
                    .moment(new Timestamp(System.currentTimeMillis()))
                    .build();

            timestampMomentRepository.save(timestampMoment);
        }

        private void resourceDemo() {
            Resource resource = Resource.builder()
                    .commonInfo(CommonInfo.builder()
                            .code("res A code")
                            .name("res A name")
                            .description("res A descr")
                            .build())
                    .type("res A type")
                    .build();

            Resource saved = resourceRepository.save(resource);
            System.out.println("resource saved:" + saved);
            Optional<Resource> byId = resourceRepository.findById(saved.getId());
            if (byId.isPresent()) {
                System.out.println("found by id:" + byId.get());
            } else {
                System.out.println("not found by id!");
            }

            resource = Resource.builder()
                    .id(7L)
                    .type("updated type")
                    .build();

            resourceRepository.save(resource);
            Resource resourceById7 = resourceRepository.findById(7L).get();
            System.out.println(resourceById7);


            // Can't insert new entity with non-null id.
//            resource = Resource.builder()
//                    .id(100L)
//                    .type("inserted with id 100L")
//                    .build();
//            resourceRepository.save(resource);
//            Resource resourceById100 = resourceRepository.findById(100L).get();
//            System.out.println(resourceById100);

        }

        private void tryToSaveNanos() {
            LocalDateTime localDateTime = LocalDateTime.of(
                    2023,
                    Month.APRIL,
                    13,
                    23,
                    23,
                    00,
                    123456789);

            InstantMoment instantMoment = InstantMoment.builder()
                    .moment(localDateTime.toInstant(ZoneOffset.UTC))
                    .build();

            System.out.println(instantMoment);
            instantMomentRepository.save(instantMoment);
        }

    }

}

package mio68.lab.spring.data.jdbc;

import mio68.lab.spring.data.jdbc.entity.CommonInfo;
import mio68.lab.spring.data.jdbc.entity.InstantMoment;
import mio68.lab.spring.data.jdbc.entity.Resource;
import mio68.lab.spring.data.jdbc.entity.TimestampMoment;
import mio68.lab.spring.data.jdbc.repository.InstantMomentRepository;
import mio68.lab.spring.data.jdbc.repository.ResourceRepository;
import mio68.lab.spring.data.jdbc.repository.TimestampMomentRepository;
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

@SpringBootApplication
public class SpringDataJdbcApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringDataJdbcApplication.class, args);
    }

    @Component
    static class SpringDataJdbcApplicationDemo implements ApplicationRunner {

        private final ResourceRepository resourceRepository;
        private final TimestampMomentRepository timestampMomentRepository;
        private final InstantMomentRepository instantMomentRepository;

        SpringDataJdbcApplicationDemo(
                ResourceRepository resourceRepository,
                TimestampMomentRepository timestampMomentRepository,
                InstantMomentRepository instantMomentRepository) {

            this.resourceRepository = resourceRepository;
            this.timestampMomentRepository = timestampMomentRepository;
            this.instantMomentRepository = instantMomentRepository;
        }

        @Override
        public void run(ApplicationArguments args) throws Exception {
            resourceDemo();
//            timestampMomentDemo();
//            instantMomentDemo();
//            tryToSaveNanos();
//            printInstantMoments();
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

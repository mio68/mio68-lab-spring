package mio68.lab.spring.test;

import mio68.lab.spring.test.repository.BookRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
class TestApplicationDemo implements ApplicationRunner {
    private final BookRepository bookRepository;

    TestApplicationDemo(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        bookRepository.findAll().forEach(System.out::println);
    }
}

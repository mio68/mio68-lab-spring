package mio68.lab.spring.test.service;

import mio68.lab.spring.test.ApplicationContextHelper;
import mio68.lab.spring.test.entity.Book;
import mio68.lab.spring.test.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// Test only BookService with @TestConfiguration
// Attention!!!
// If you want to customize the primary configuration, you can use a nested
// @TestConfiguration class.
// Unlike a nested @Configuration class, which would be used instead of your application’s
// primary configuration, a nested @TestConfiguration class is used in addition
// to your application’s primary configuration.
@SpringBootTest
public class BookServiceTestWithTestConfiguration {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    BookService bookService;


    @BeforeEach
    void setUp() {
        ApplicationContextHelper.printInfo(applicationContext);
    }

    @Test
    void findByIsbn() {
        Optional<Book> foundByIsbn = bookService.findByIsbn("978-0-13-468599-1");
        assertTrue(foundByIsbn.isPresent(), "Must be found!");
    }

    @TestConfiguration
    public static class BookServiceTestConfiguration {

        @Bean
        public BookRepository bookRepository() {
            return new BookRepository() {
                @Override
                public Optional<Book> findByIsbn(String isbn) {
                    if ("978-0-13-468599-1".equals(isbn)) {
                        return Optional.of(new Book(1,"978-0-13-468599-1", "Title", "Author" ));
                    } else {
                        return Optional.empty();
                    }
                }

                @Override
                public <S extends Book> S save(S entity) {
                    return null;
                }

                @Override
                public <S extends Book> Iterable<S> saveAll(Iterable<S> entities) {
                    return null;
                }

                @Override
                public Optional<Book> findById(Integer integer) {
                    return Optional.empty();
                }

                @Override
                public boolean existsById(Integer integer) {
                    return false;
                }

                @Override
                public Iterable<Book> findAll() {
                    return Collections.EMPTY_LIST;
                }

                @Override
                public Iterable<Book> findAllById(Iterable<Integer> integers) {
                    return null;
                }

                @Override
                public long count() {
                    return 0;
                }

                @Override
                public void deleteById(Integer integer) {

                }

                @Override
                public void delete(Book entity) {

                }

                @Override
                public void deleteAllById(Iterable<? extends Integer> integers) {

                }

                @Override
                public void deleteAll(Iterable<? extends Book> entities) {

                }

                @Override
                public void deleteAll() {

                }
            };
        }

    }
}
package mio68.lab.spring.test.repository;

import mio68.lab.spring.test.ApplicationContextHelper;
import mio68.lab.spring.test.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.ApplicationContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

// Sliced test for Spring Data Jdbc
@DataJdbcTest
class BookRepositoryTest {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        ApplicationContextHelper.printInfo(applicationContext);
    }

    @Test
    public void givenDefaultBooks_whenFind_thenFound() {
        Optional<Book> foundByIsbn = bookRepository.findByIsbn("978-0-13-468599-1");
        assertTrue(foundByIsbn.isPresent(), "Must be found!");
    }

}
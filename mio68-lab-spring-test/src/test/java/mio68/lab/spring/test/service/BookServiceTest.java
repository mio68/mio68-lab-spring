package mio68.lab.spring.test.service;

import mio68.lab.spring.test.ApplicationContextHelper;
import mio68.lab.spring.test.entity.Book;
import mio68.lab.spring.test.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// Test only BookService.
@SpringBootTest(classes = BookService.class)
public class BookServiceTest {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    BookService bookService;

    @MockBean
    BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        ApplicationContextHelper.printInfo(applicationContext);
        when(bookRepository.findByIsbn(any())).thenReturn(
                Optional.of(new Book(1, "978-0-13-468599-1", "Title", "Author")));
    }

    @Test
    void findByIsbn() {
        Optional<Book> foundByIsbn = bookService.findByIsbn("978-0-13-468599-1");
        assertTrue(foundByIsbn.isPresent(), "Must be found!");
    }
}
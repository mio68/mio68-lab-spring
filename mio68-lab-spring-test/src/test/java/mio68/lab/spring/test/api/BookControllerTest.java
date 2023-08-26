package mio68.lab.spring.test.api;

import mio68.lab.spring.test.ApplicationContextHelper;
import mio68.lab.spring.test.entity.Book;
import mio68.lab.spring.test.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Sliced test for BookController
@WebMvcTest(controllers = BookController.class)
class BookControllerTest {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    MockMvc mockMvc;

    // Mock beans are automatically reset after each test method.
    @MockBean
    BookService bookService;

    @BeforeEach
    void setUp() {
        ApplicationContextHelper.printInfo(applicationContext);
        when(bookService.findByIsbn("1")).thenReturn(Optional.empty());
        when(bookService.findByIsbn("2")).thenReturn(Optional.of(
                new Book(1, "2", "Title", "Author")
        ));
    }

    @Test
    void givenNotExists_findByIsbn_ThenNoContent() throws Exception {
        mockMvc.perform(get("/api/v1/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void givenBookExists_findByIsbn_ThenOK() throws Exception {
        mockMvc.perform(get("/api/v1/books/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}
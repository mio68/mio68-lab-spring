package mio68.lab.spring.test.api;

import mio68.lab.spring.test.entity.Book;
import mio68.lab.spring.test.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController("mio68.lab.spring.test.api.BookController")
@RequestMapping("/api/v1")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books/{isbn}")
    public ResponseEntity<Book> findByIsbn(@PathVariable String isbn) {
        Optional<Book> optionalBook = bookService.findByIsbn(isbn);
        return optionalBook.map(book -> new ResponseEntity<>(book, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));

    }
}

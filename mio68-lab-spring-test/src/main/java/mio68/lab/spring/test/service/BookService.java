package mio68.lab.spring.test.service;

import mio68.lab.spring.test.entity.Book;
import mio68.lab.spring.test.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("mio68.lab.spring.test.service.BookService")
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Optional<Book> findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

}

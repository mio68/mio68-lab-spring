package mio68.lab.spring.test.repository;

import mio68.lab.spring.test.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("mio68.lab.spring.test.repository.BookRepository")
public interface BookRepository extends CrudRepository<Book, Integer> {
    Optional<Book> findByIsbn(String isbn);
}

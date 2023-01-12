package mio68.lab.spring.minwebapp;

import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@SpringBootApplication
public class MinimalWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(MinimalWebApplication.class, args);
    }

    @RestController
    @RequestMapping("/api/v1")
    static class DemoController {

        private final BookRepository bookRepository;

        DemoController(BookRepository bookRepository) {
            this.bookRepository = bookRepository;
        }

        @GetMapping("/hello")
        public String hello() {
            return "Hello World!";
        }

        @GetMapping("/books")
        public List<Book> getBooks() {
            return bookRepository.findAll();
        }

        @PostMapping("/book")
        public Book saveBook(@RequestBody Book book) {
            return bookRepository.save(book);
        }
    }

    @Repository
    static class BookRepository {
        public static final String FIND_ALL_QUERY = "SELECT * FROM books";
        public static final String INSERT_BOOK_QUERY = "INSERT INTO books (isbn, title, authors) VALUES (?,?,?)";
        private final JdbcTemplate jdbcTemplate;

        BookRepository(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }

        public List<Book> findAll() {
            return jdbcTemplate.query(
                    FIND_ALL_QUERY,
                    (rs, rowNum) -> mapRowToBook(rs));
        }

        public Book save(Book book) {
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(
                    con -> {
                        PreparedStatement preparedStatement = con.prepareStatement(
                                INSERT_BOOK_QUERY,
                                Statement.RETURN_GENERATED_KEYS);
                        preparedStatement.setString(1, book.getIsbn());
                        preparedStatement.setString(2, book.getTitle());
                        preparedStatement.setString(3, book.getAuthors());
                        return preparedStatement;
                    },
                    keyHolder);
            return new Book(
                    keyHolder.getKey() == null ? 0 : keyHolder.getKey().intValue(),
                    book.getIsbn(),
                    book.getTitle(),
                    book.getAuthors());
        }

        private Book mapRowToBook(ResultSet rs) throws SQLException {
            return new Book(
                    rs.getInt("id"),
                    rs.getString("isbn"),
                    rs.getString("title"),
                    rs.getString("authors"));
        }
    }

    @Data
    static class Book {
        private final int id;
        private final String isbn;
        private final String title;
        private final String authors;
    }

}

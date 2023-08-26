package mio68.lab.spring.test.entity;

import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Value
@Table("BOOKS")
public class Book {
    @Id
    int id;
    String isbn;
    String title;
    String authors;
}

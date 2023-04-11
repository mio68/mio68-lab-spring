package mio68.lab.spring.jpa.timefield.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    private String name = "default name";

//    private Instant createdOn = Instant.now();
    private Timestamp createdOn = new Timestamp(System.currentTimeMillis());

}

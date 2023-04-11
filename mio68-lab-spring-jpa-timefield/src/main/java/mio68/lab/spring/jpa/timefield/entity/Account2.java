package mio68.lab.spring.jpa.timefield.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

public class Account2 {
    @Id
    @GeneratedValue
    private Long id;

    private String name = "default name";

    //    private Instant createdOn = Instant.now();
    private Timestamp createdOn = new Timestamp(System.currentTimeMillis());
}

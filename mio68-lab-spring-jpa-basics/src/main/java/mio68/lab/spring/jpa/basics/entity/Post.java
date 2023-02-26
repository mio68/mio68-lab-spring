package mio68.lab.spring.jpa.basics.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Post")
@Table(name = "post")
@Data
public class Post {

    @Id
    private Long id;

    private String title;

}
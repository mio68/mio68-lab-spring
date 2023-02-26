package mio68.lab.spring.jpa.basics.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "PostComment")
@Table(name = "post_comment")
@Data
public class PostComment {

    @Id
    private Long id;

//    FetchType.LAZY can be used to prevent N+1 problem
//    WARNING! It's impossible to get associated entity lazily without transaction
//    when Entity manager is closed.
    @ManyToOne (fetch = FetchType.LAZY)
//    @ManyToOne
    private Post post;

    private String review;

}
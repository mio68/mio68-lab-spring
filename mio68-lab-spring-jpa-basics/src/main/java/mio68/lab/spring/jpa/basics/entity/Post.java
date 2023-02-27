package mio68.lab.spring.jpa.basics.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "Post")
@Table(name = "post")
@Data
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

//    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
//    private PostDetails details;
//
//    public void setDetails(PostDetails details) {
//        if (details == null) {
//            if (this.details != null) {
//                this.details.setPost(null);
//            }
//        }
//        else {
//            details.setPost(this);
//        }
//        this.details = details;
//    }
}
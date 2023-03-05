package mio68.lab.spring.jpa.basics.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Post")
@Table(name = "post")
@Data
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @OneToOne(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private PostDetails details;

    // Bidirectional One-to-Many is useful for cascade deleting child entities.
    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PostComment> comments = new ArrayList<>();

    public void setDetails(PostDetails details) {
        if (details != null) {
            details.setPost(this);
        }
        this.details = details;
    }

}
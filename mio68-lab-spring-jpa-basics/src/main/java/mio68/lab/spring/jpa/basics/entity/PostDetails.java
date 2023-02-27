package mio68.lab.spring.jpa.basics.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "PostDetails")
@Table(name = "post_details")
@Data
public class PostDetails {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "created_on")
    private Date createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @OneToOne
//    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public PostDetails() {}

    public PostDetails(String createdBy) {
        createdOn = new Date();
        this.createdBy = createdBy;
    }

}
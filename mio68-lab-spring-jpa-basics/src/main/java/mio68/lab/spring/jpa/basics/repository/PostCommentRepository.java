package mio68.lab.spring.jpa.basics.repository;

import mio68.lab.spring.jpa.basics.entity.PostComment;

import java.util.List;

public interface PostCommentRepository {
    List<PostComment> findAllNPlusOneDemo();

    void persist(PostComment postComment);

    void removeCommentsByPostId(Long postId);
}

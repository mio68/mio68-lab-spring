package mio68.lab.spring.jpa.basics.repository.post;

import mio68.lab.spring.jpa.basics.entity.Post;

import java.util.Optional;

public interface PostCustomRepository {
     Post persist(Post post);
     Post merge(Post post);

    Optional<Post> findPostWithDetails(Long id);

    Optional<Post> findPostWithDetailsAndComments(Long id);
}

package mio68.lab.spring.jpa.basics.repository.post;

import mio68.lab.spring.jpa.basics.entity.Post;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

public class PostCustomRepositoryImpl implements PostCustomRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public PostCustomRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Post persist(Post post) {
        entityManager.persist(post);
        return post;
    }

    @Override
    public Post merge(Post post) {
        return entityManager.merge(post);
    }

    // Details are optional, so LEFT JOIN FETCH is used
    @Override
    public Optional<Post> findPostWithDetails(Long id) {
        return entityManager.createQuery("""
                                SELECT p
                                FROM Post p
                                LEFT JOIN FETCH p.details d
                                WHERE p.id=:id
                                """,
                        Post.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findAny();
    }

}

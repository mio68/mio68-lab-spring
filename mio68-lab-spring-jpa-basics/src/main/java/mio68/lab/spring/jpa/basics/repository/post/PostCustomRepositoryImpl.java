package mio68.lab.spring.jpa.basics.repository.post;

import mio68.lab.spring.jpa.basics.entity.Post;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
}

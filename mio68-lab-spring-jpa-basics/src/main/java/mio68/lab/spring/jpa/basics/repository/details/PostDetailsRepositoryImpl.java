package mio68.lab.spring.jpa.basics.repository.details;


import mio68.lab.spring.jpa.basics.entity.PostDetails;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class PostDetailsRepositoryImpl implements PostDetailsRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public PostDetailsRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void persist(PostDetails postDetails) {
        entityManager.persist(postDetails);
    }

    @Override
    public void remove(PostDetails postDetails) {
        entityManager.remove(postDetails);
    }

    @Override
    public PostDetails find(Long id) {
        return entityManager.find(PostDetails.class, id);
    }
}

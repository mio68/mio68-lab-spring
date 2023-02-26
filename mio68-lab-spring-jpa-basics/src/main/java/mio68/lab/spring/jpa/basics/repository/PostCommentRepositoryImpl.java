package mio68.lab.spring.jpa.basics.repository;

import mio68.lab.spring.jpa.basics.entity.PostComment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class PostCommentRepositoryImpl implements PostCommentRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public PostCommentRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    @Transactional // Required to use lazy initialized post
    public List<PostComment> findAllNPlusOneDemo() {

        List<PostComment> postCommentList = entityManager.createQuery("""
                SELECT pc
                FROM PostComment pc
                """,
                PostComment.class)
                .getResultList();

        // This traversing produces N+1 problem even with Lazy fetching
        postCommentList.forEach(postComment -> postComment.getPost().getTitle());

        return postCommentList;
    }

    // Avoid N+1 problem with JOIN FETCH, and get post comments with their post
    // using one SQL query
    public List<PostComment> findAllWithJoinFetch() {
        return entityManager.createQuery("""
                SELECT pc
                FROM PostComment pc
                JOIN FETCH pc.post p
                """,
                        PostComment.class)
                .getResultList();
    }

}

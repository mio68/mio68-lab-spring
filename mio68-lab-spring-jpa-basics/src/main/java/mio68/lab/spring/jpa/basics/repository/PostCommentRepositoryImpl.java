package mio68.lab.spring.jpa.basics.repository;

import mio68.lab.spring.jpa.basics.entity.PostComment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PostCommentRepositoryImpl implements PostCommentRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public PostCommentRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    // This method produces N sub-queries if fetch is eager.
    // If fetch is lazy then it can produce N sub-queries at traversing if parent post property is accessed.
    @Override
    public List<PostComment> findAllNPlusOneDemo() {
        return entityManager.createQuery("""
                SELECT pc
                FROM PostComment pc
                """,
                PostComment.class)
                .getResultList();
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


    // If FetchType.EAGER then find will use single select request with left outer join if @ManyToOne(optional = true)
    // or with inner join if post @ManyToOne(optional = false)
    public PostComment find(Long id) {
        return entityManager.find(PostComment.class, id);
    }

    // JOIN FETCH causes single select request and this doesn't depend on FetchType (LAZY or EAGER)
    // post is joined with inner join
    public PostComment findPostCommentWithPost(Long id) {
        return entityManager.createQuery("""
                                SELECT pc
                                FROM PostComment pc
                                JOIN FETCH pc.post p
                                WHERE pc.id=:id
                                """,
                        PostComment.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public void persist(PostComment postComment) {
        entityManager.persist(postComment);
    }

    @Override
    public void removeCommentsByPostId(Long postId) {
        entityManager.createQuery("""
                DELETE PostComment pc WHERE pc.post.id=:postId
                """)
                .setParameter("postId", postId)
                .executeUpdate();
    }
}

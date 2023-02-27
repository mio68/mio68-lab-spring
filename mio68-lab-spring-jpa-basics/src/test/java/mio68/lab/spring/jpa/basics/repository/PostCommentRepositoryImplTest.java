package mio68.lab.spring.jpa.basics.repository;

import mio68.lab.spring.jpa.basics.entity.Post;
import mio68.lab.spring.jpa.basics.entity.PostComment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@SpringBootTest
class PostCommentRepositoryImplTest {

    @Autowired
    PostCommentRepositoryImpl postCommentRepository;

    @Autowired
    PlatformTransactionManager platformTransactionManager;

    TransactionTemplate transactionTemplate;

    @BeforeEach
    public void setUp() {
        transactionTemplate = new TransactionTemplate(platformTransactionManager);
    }

    @Test
    public void nPlusOneProblemDemo() {
        // see log
        transactionTemplate.executeWithoutResult(
                status -> postCommentRepository.findAllNPlusOneDemo()
                        // This traversing produces N+1 problem even with Lazy fetching
                        .forEach(System.out::println)
        );
    }

    @Test
    public void avoidNPlusOneProblemWithJoinFetch() {
        // see log
        postCommentRepository.findAllWithJoinFetch()
                .forEach(System.out::println);
    }

    // If FetchType.EAGER then find will use single select request with left outer join if @ManyToOne(optional = true)
    // or with inner join if post @ManyToOne(optional = false).
    // If FetchType.LAZY then find will use extra select request to get corresponding post
    @Test
    public void testFind() {
        transactionTemplate.executeWithoutResult(
                status -> {
                    PostComment postComment = postCommentRepository.find(1L);
                    Post post = postComment.getPost();
                    // Getting title produces extra request to post table if FetchType.LAZY
                    String title = post.getTitle();
                    System.out.println("post title: " + title);
                }
        );
    }

    // JOIN FETCH causes single select request and this doesn't depend on FetchType (LAZY or EAGER)
    // post is joined with inner join
    @Test
    public void tesFindPostCommentWithPost() {
        transactionTemplate.executeWithoutResult(
                status -> {
                    PostComment postComment = postCommentRepository.findPostCommentWithPost(1L);
                    Post post = postComment.getPost();
                    // Getting title doesn't produce extra request to post table
                    // because using JOIN FETCH in the findPostCommentWithPost method
                    String title = post.getTitle();
                    System.out.println("post title: " + title);
                }
        );
    }

}
package mio68.lab.spring.jpa.basics.repository.post;

import mio68.lab.spring.jpa.basics.entity.Post;
import mio68.lab.spring.jpa.basics.entity.PostComment;
import mio68.lab.spring.jpa.basics.entity.PostDetails;
import mio68.lab.spring.jpa.basics.repository.PostCommentRepository;
import mio68.lab.spring.jpa.basics.repository.details.PostDetailsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostDetailsRepository postDetailsRepository;

    @Autowired
    PostCommentRepository postCommentRepository;

    @Autowired
    PlatformTransactionManager platformTransactionManager;

    TransactionTemplate transactionTemplate;


    @BeforeEach
    public void setUp() {
        transactionTemplate = new TransactionTemplate(platformTransactionManager);
    }

    @AfterEach
    public void tearDown() {
//        transactionTemplate.executeWithoutResult(
//                status -> postRepository.delete(existingPost));
    }

    @Test
    public void whenSavePost_thenFindPost() {
        Post post = new Post();
        post.setTitle("new post");

        Post postSaved = transactionTemplate.execute(
                status -> postRepository.persist(post));

        Post postFound = transactionTemplate.execute(
                status -> postRepository.findById(post.getId()).orElseThrow());

        assertEquals(postSaved, postFound, "saved and found are not equal!");

        transactionTemplate.executeWithoutResult(
                status -> postRepository.delete(post));

        Optional<Post> optionalPost = transactionTemplate.execute(
                status -> postRepository.findById(post.getId()));

        assertTrue(optionalPost.isEmpty(), "deleted by found again!");
    }

    @Test
    public void savePostWithDetails() {
        Post post = new Post();
        post.setTitle("post");
        post.setDetails(new PostDetails("Bob"));

        Post postSaved = transactionTemplate.execute(
                status -> postRepository.persist(post));

        System.out.println(postSaved);
    }

    // Two requests are used to get post, and it's post details
    @Test
    public void findPost1() {
        Post post = transactionTemplate.execute(
                status -> postRepository.findById(1L)).orElseThrow();
        System.out.println(post);
    }


    // Two requests are used to get post, and it's post details
    @Test
    public void findPost25() {
        Post post = transactionTemplate.execute(
                status -> postRepository.findById(25L)).orElseThrow();
        System.out.println(post);
    }

    @Test
    public void findPostWithDetails1() {
        Post post = transactionTemplate.execute(
                status -> postRepository.findPostWithDetails(1L)).orElseThrow();
        System.out.println(post);
    }

    // it uses only one select query with inner join
    @Test
    public void findPostWithDetails25() {
        Post post = transactionTemplate.execute(
                status -> postRepository.findPostWithDetails(25L)).orElseThrow();
        System.out.println(post);
    }

    @Test
    public void emptyOptionalIfNotFound() {
        Optional<Post> optionalPost = transactionTemplate.execute(
                status -> postRepository.findPostWithDetails(0L));
        assertTrue(optionalPost.isEmpty());
    }

    @Test
    public void saveNewPostWithDetailsBySavingPostDetails() {
        Post post = new Post();
        post.setTitle("new post");
        PostDetails postDetails = new PostDetails("Bob");
        postDetails.setPost(post);

        transactionTemplate.executeWithoutResult(
                status -> postDetailsRepository.persist(postDetails));

        PostDetails foundDetails = transactionTemplate.execute(
                status -> postDetailsRepository.find(postDetails.getId())
        );

        assertEquals(postDetails, foundDetails);
    }

    @Test
    public void attachDetails() {
        Post post = new Post();
        post.setTitle("new post");

        transactionTemplate.executeWithoutResult(
                status -> postRepository.persist(post));

        // post is detached now
        PostDetails postDetails = new PostDetails("Bob");
        transactionTemplate.executeWithoutResult(
                status -> postDetailsRepository.attachDetails(post.getId(), postDetails));

    }


    @Test
    public void tryToSaveTwoDetailsForOnePost() {
        Post post = new Post();
        post.setTitle("new post");

        transactionTemplate.executeWithoutResult(
                status -> postRepository.persist(post));

        PostDetails postDetails = new PostDetails("Bob");

        PostDetails anotherPostDetails = new PostDetails("Alice");

        transactionTemplate.executeWithoutResult(
                status -> postDetailsRepository.attachDetails(post.getId(), postDetails));

        transactionTemplate.executeWithoutResult(
                status -> postDetailsRepository.attachDetails(post.getId(), anotherPostDetails));

    }

    @Test
    public void createPostWith2Comments() {
        Post post = new Post();
        post.setTitle("post with details and a comment");

        PostComment postComment = new PostComment();
        postComment.setPost(post);
        postComment.setReview("will JPA remove post comment if post is removed?");
        post.getComments().add(postComment);

        postComment = new PostComment();
        postComment.setPost(post);
        postComment.setReview("post with two comments)");
        post.getComments().add(postComment);

        transactionTemplate.executeWithoutResult(
                status -> postRepository.persist(post));

        transactionTemplate.executeWithoutResult(
                status -> {
                    // findById will use 2 select queries, and lazy get for comments will use
                    // one more select
//                    Post postFound = postRepository.findById(post.getId()).orElseThrow();

                    Post postFound = postRepository.findPostWithDetailsAndComments(post.getId()).orElseThrow();
                    System.out.println(postFound);
                });

    }

    @Test
    public void createPostWithDetailsAndAComment_andWhenDeleteIt() {
        Post post = new Post();
        post.setTitle("post with details and a comment");

        PostDetails postDetails = new PostDetails("Bob");
        post.setDetails(postDetails);

        PostComment postComment = new PostComment();
        postComment.setPost(post);
        postComment.setReview("will JPA remove post comment if post is removed?");
        post.getComments().add(postComment);

        postComment = new PostComment();
        postComment.setPost(post);
        postComment.setReview("another comment2");
        post.getComments().add(postComment);

        transactionTemplate.executeWithoutResult(
                status -> postRepository.persist(post));

        System.out.println(post);

        transactionTemplate.executeWithoutResult(
                status -> {

                    // remove comments, then remove post
                    postCommentRepository.removeCommentsByPostId(post.getId());
                    postRepository.deleteById(post.getId());

                    // Select everything with one select
//                    Post postFound = postRepository.findPostWithDetailsAndComments(post.getId()).orElseThrow();
                    // deleteById() will select post, post_details, post_comment by
                    // separate select query (3 in total)
                    // Delete with many delete queries
                    // Each comment will be deleted by separate query
//                    postRepository.deleteById(post.getId());
                });

    }

}
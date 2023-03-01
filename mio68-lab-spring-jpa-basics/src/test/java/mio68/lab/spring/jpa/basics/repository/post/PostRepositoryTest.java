package mio68.lab.spring.jpa.basics.repository.post;

import mio68.lab.spring.jpa.basics.entity.Post;
import mio68.lab.spring.jpa.basics.entity.PostDetails;
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

@SpringBootTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostDetailsRepository postDetailsRepository;

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

}
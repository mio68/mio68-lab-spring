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
    public void saveNewPostWithDetails() {
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
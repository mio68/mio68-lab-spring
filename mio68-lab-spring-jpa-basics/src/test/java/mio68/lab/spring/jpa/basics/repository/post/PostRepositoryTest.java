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

    Post existingPost;

    @BeforeEach
    public void setUp() {
        transactionTemplate = new TransactionTemplate(platformTransactionManager);

        Post post = new Post();
        post.setTitle("new post");

        existingPost = transactionTemplate.execute(
                status -> postRepository.persist(post));

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
    public void whenSaveDetails_thenFound() {
        PostDetails postDetails = new PostDetails("Bob");
        postDetails.setPost(existingPost);

        transactionTemplate.executeWithoutResult(
                status -> postDetailsRepository.persist(postDetails));

        PostDetails foundDetails = transactionTemplate.execute(
                status -> postDetailsRepository.find(postDetails.getId())
        );

        assertTrue(postDetails.equals(foundDetails));
    }

    @Test
    public void tryToSaveTwoDetailsForOnePost() {
        PostDetails postDetails = new PostDetails("Bob");
        postDetails.setPost(existingPost);

        PostDetails anotherPostDetails = new PostDetails("Alice");
        anotherPostDetails.setPost(existingPost);

        transactionTemplate.executeWithoutResult(
                status -> postDetailsRepository.persist(postDetails));

        transactionTemplate.executeWithoutResult(
                status -> postDetailsRepository.persist(anotherPostDetails));

    }

}
package mio68.lab.spring.jpa.basics.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostCommentRepositoryImplTest {

    @Autowired
    PostCommentRepositoryImpl postCommentRepository;

    @Test
    public void nPlusOneProblemDemo() {
        // see log
        postCommentRepository.findAllNPlusOneDemo()
                .forEach(System.out::println);
    }

    @Test
    public void avoidNPlusOneProblemWithJoinFetch() {
        // see log
        postCommentRepository.findAllWithJoinFetch()
                .forEach(System.out::println);
    }

}
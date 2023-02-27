package mio68.lab.spring.jpa.basics.repository.post;

import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends PostSpringDataRepository, PostCustomRepository {
}

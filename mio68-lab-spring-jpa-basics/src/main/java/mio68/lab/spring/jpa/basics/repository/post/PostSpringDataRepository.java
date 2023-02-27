package mio68.lab.spring.jpa.basics.repository.post;

import mio68.lab.spring.jpa.basics.entity.Post;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@NoRepositoryBean
public interface PostSpringDataRepository extends Repository<Post, Long> {

    Optional<Post> findById(Long id);
    Post getReferenceById(Long id);

    void deleteById(Long id);
    void delete(Post entity);

}
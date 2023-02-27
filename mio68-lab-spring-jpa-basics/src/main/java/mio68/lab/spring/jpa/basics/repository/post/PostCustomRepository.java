package mio68.lab.spring.jpa.basics.repository.post;

import mio68.lab.spring.jpa.basics.entity.Post;

public interface PostCustomRepository {
     Post persist(Post post);
     Post merge(Post post);
}

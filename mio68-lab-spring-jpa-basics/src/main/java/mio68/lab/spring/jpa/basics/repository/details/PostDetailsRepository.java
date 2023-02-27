package mio68.lab.spring.jpa.basics.repository.details;

import mio68.lab.spring.jpa.basics.entity.PostDetails;

public interface PostDetailsRepository {
    void persist(PostDetails postDetails);

    void remove(PostDetails postDetails);

    PostDetails find(Long id);
}

package mio68.lab.spring.profiling.service;

import mio68.lab.spring.profiling.model.MyUser;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserServiceImpl implements UserService {

    private final AtomicLong idCounter = new AtomicLong();

    private final List<MyUser> myUsers = new ArrayList<>();

    @Override
    public Optional<MyUser> findById(Long id) {
        return myUsers.stream().filter(myUser -> myUser.getId().equals(id)).findAny();
    }

    @Override
    public MyUser crateRandomUser() {
        MyUser myUser = new MyUser();
        Long id = idCounter.incrementAndGet();
        myUser.setId(id);
        myUser.setName("User-" + id + "_________________________________" + Instant.now());
        myUsers.add(myUser);
        return myUser;
    }

}

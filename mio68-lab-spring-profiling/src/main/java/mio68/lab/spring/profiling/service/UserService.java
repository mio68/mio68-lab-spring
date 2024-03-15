package mio68.lab.spring.profiling.service;

import mio68.lab.spring.profiling.model.MyUser;

import java.util.Optional;

public interface UserService {

    Optional<MyUser> findById(Long id);

    MyUser crateRandomUser();

}

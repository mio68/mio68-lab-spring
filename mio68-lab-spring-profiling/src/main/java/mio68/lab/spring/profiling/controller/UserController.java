package mio68.lab.spring.profiling.controller;

import mio68.lab.spring.profiling.model.MyUser;
import mio68.lab.spring.profiling.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/rnd/users")
    public MyUser getRandomUser() {
        return userService.crateRandomUser();
    }

}

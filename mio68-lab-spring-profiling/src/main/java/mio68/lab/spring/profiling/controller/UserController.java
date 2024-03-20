package mio68.lab.spring.profiling.controller;

import lombok.RequiredArgsConstructor;
import mio68.lab.spring.profiling.model.MyUser;
import mio68.lab.spring.profiling.service.UserService;
import mio68.lab.spring.profiling.service.perf.ServiceA;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class UserController {

    private final ServiceA serviceA;
    private final UserService userService;

    @GetMapping("/rnd/users")
    public MyUser getRandomUser() {
        return userService.crateRandomUser();
    }

    @GetMapping("/report")
    public String getReport() {
        return serviceA.getReport();
    }

}

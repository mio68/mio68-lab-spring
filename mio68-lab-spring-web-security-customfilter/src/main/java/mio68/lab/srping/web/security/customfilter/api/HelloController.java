package mio68.lab.srping.web.security.customfilter.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        log.debug("hello");
        return "Hello World!";
    }

    @GetMapping("/hi")
    public String hi() {
        log.debug("hi");
        return "Hi!";
    }

}

package mio68.lab.spring.web.validation.controller;

import lombok.extern.slf4j.Slf4j;
import mio68.lab.spring.web.validation.constraint.ValidFieldName;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hello")
@Slf4j
@Validated
public class HelloController {

    @GetMapping("/{name}")
    public String hello(@PathVariable @ValidFieldName String name) {
        return "Hello " + name + " !";
    }
}

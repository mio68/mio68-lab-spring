package mio68.lab.spring.runner.service;

import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello() {
        return "Hello World!";
    }

}

package mio68.lab.spring.jpa.basics.service;

import mio68.lab.spring.jpa.basics.JpaBasicsDemoApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {JpaBasicsDemoApplication.class})
class DemoServiceTest {

    @Autowired
    DemoService demoService;


}
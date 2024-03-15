package mio68.lab.spring.profiling;

import mio68.lab.spring.profiling.model.MyUser;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Prof {
    public static void main(String[] args) {
        SpringApplication.run(Prof.class, args);
    }
}

@Component
class ProfDemo implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(VM.current().details());
        System.out.println(ClassLayout.parseClass(MyUser.class).toPrintable());
    }
}
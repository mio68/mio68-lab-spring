package mio68.lab.spring.debug;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DebugApplication implements ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(DebugApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Press <Enter> to shutdown...");
        int c = System.in.read();
        String s = "Hello world!";
        int k = c + 10; // just for breakpoint
    }
}

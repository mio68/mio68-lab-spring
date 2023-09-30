package mio68.lab.spring.actuator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableAsync
@Slf4j
public class ActuatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActuatorApplication.class, args);
    }

    @Component
    public static class TaskExecutorDemo implements ApplicationRunner {
        private final TaskExecutor taskExecutor;

        public TaskExecutorDemo(TaskExecutor taskExecutor) {
            this.taskExecutor = taskExecutor;
        }

        @Override
        public void run(ApplicationArguments args) {
            class Task implements Runnable {
                String name;
                Task(String name) {
                    this.name = name;
                }

                @Override
                public void run() {
                    try {
                        for (int i = 0; i < 600; i++) {
                            log.info("Task A, iteration: " + i);
                            TimeUnit.SECONDS.sleep(1);
                        }
                    } catch (InterruptedException e) {
                        log.warn("interrupted");
                    }
                }
            }

            taskExecutor.execute(new Task("Task A"));
            taskExecutor.execute(new Task("Task B"));

        }
    }

}

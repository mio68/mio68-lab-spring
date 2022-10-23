package mio68.lab.spring.taskexecutor.service;

import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class SomeServiceImpl implements SomeService{

    private final TaskExecutor taskExecutor;

    public SomeServiceImpl(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    @Override
    public int getIt() {
        return 0;
    }
}

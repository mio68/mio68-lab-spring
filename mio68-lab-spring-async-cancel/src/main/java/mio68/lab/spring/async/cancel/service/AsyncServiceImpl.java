package mio68.lab.spring.async.cancel.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AsyncServiceImpl implements AsyncService {

    private final TaskExecutor taskExecutor;

    public AsyncServiceImpl(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
        log.debug("taskExecutor:" + taskExecutor.getClass());
    }

    // This version doesn't stop execution when cancelled!
    @Override
    @Async
    public CompletableFuture<String> submit(long iterationNumber, int delaySec) {
        log.debug("submit");
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        for (long i = 0; i < iterationNumber; i++) {
            log.debug("iteration {}", i);

            // test interruption
            if (Thread.currentThread().isInterrupted()) {
                log.debug("iteration interrupted");
                break;
            }

            // delay if required
            if (delaySec > 0) {
                try {
                    TimeUnit.SECONDS.sleep(delaySec);
                } catch (InterruptedException e) {
                    completableFuture.completeExceptionally(e);
                    log.debug("completed exceptionally");
                    return completableFuture;
                }
            }
        }
        completableFuture.complete(
                "Completed with %d iteration".formatted(iterationNumber));
        log.debug("completed normally");
        return completableFuture;
    }

    // This doesn't start at all!
    @Override
    @Async
    public Future<String> submitWithFuture(long iterationNumber, int delaySec) {
        return new FutureTask<>(() -> {
            log.debug("submit");
            for (long i = 0; i < iterationNumber; i++) {
                log.debug("iteration {}", i);
                if (delaySec > 0) {
                    TimeUnit.SECONDS.sleep(delaySec);
                }
            }
            log.debug("completed normally");
            return "Completed with %d iteration".formatted(iterationNumber);
        });
    }

    // It works just fine and thread get interrupted flag by Future::cancel(true)
    @Override
    public Future<String> submitWithTaskExecutor(long iterationNumber, int delaySec) {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            log.debug("submit");
            for (long i = 0; i < iterationNumber; i++) {
                log.debug("iteration {}", i);
                // long-time operation imitation
                for (long j = 0; j < 1_000_000_000L; j++);

                // test interruption
                if (Thread.currentThread().isInterrupted()) {
                    log.debug("iteration interrupted");
                    break;
                }
                if (delaySec > 0) {
                    TimeUnit.SECONDS.sleep(delaySec);
                }
            }
            log.debug("completed normally");
            return "Completed with %d iteration".formatted(iterationNumber);
        });
        taskExecutor.execute(futureTask);
        return futureTask;
    }




}

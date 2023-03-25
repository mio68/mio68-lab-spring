package mio68.lab.spring.async.cancel;

import lombok.extern.slf4j.Slf4j;
import mio68.lab.spring.async.cancel.service.AsyncService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableAsync
@Slf4j
public class AsyncCancelApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsyncCancelApplication.class, args);
    }

    @Component
    public static class AsyncCancelDemo implements ApplicationRunner {

        public static final int TIMEOUT = 5;
        private final AsyncService asyncService;

        public AsyncCancelDemo(AsyncService asyncService) {
            this.asyncService = asyncService;
        }

        @Override
        public void run(ApplicationArguments args) throws Exception {
            submitWithCompletableFuture();
//            submitWithFuture();
//            submitWithTaskExecutor();
        }

        private void submitWithCompletableFuture() throws InterruptedException {
            CompletableFuture<String> completableFuture =
                    asyncService.submit(100, 1);

            TimeUnit.SECONDS.sleep(TIMEOUT);
            completableFuture.cancel(true);

            try {
                log.debug(completableFuture.get());
            } catch (Exception e) {
                log.debug("completableFuture.get()", e);
            }
        }

        private void submitWithFuture() throws InterruptedException {
            Future<String> future =
                    asyncService.submitWithFuture(100, 1);

            TimeUnit.SECONDS.sleep(TIMEOUT);
            future.cancel(true);

            try {
                log.debug(future.get());
            } catch (Exception e) {
                log.debug("future.get()", e);
            }
        }

        private void submitWithTaskExecutor() throws InterruptedException {
            Future<String> future =
                    asyncService.submitWithTaskExecutor(100, 0);

            TimeUnit.SECONDS.sleep(TIMEOUT);
            log.debug("future.cancel(true): " + future.cancel(true));

            try {
                log.debug(future.get());
            } catch (Exception e) {
                log.debug("future.get()", e);
            }
        }

    }

}

Async execution with cancel possibility.

1. @Async returning CompletableFuture

This method will be executed asynchronously, but execution thread isn't interrupted
by CompletableFuture::cancel. So CompletableFuture is cancelled but corresponding
execution thread will continue execution.

2. Чтобы выполняющий тред получал сигнал interrupted надо использовать FutureTask и Executor.
см. AsyncServiceImpl::submitWithTaskExecutor


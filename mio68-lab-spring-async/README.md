1. Async method that is going to return Integer.

Async method immediately returns NULL to client and then executes asynchronously and never returns it's 
actual value to client. Bad idea!

```
    @Override
    @Async
    public Integer getRate() throws InterruptedException {
```

Console output:
```
2022-10-23 17:54:00.385  INFO 17644 --- [           main] mio68.lab.spring.async.AsyncApplication  : Starting AsyncApplication using Java 17 on LAPTOP-UNVDQTPM with PID 17644 (C:\Users\Igor\IdeaProjects\mio68-lab-spring\mio68-lab-spring-async\target\classes started by Igor in C:\Users\Igor\IdeaProjects\mio68-lab-spring)
2022-10-23 17:54:00.387  INFO 17644 --- [           main] mio68.lab.spring.async.AsyncApplication  : No active profile set, falling back to 1 default profile: "default"
2022-10-23 17:54:00.868  INFO 17644 --- [           main] mio68.lab.spring.async.AsyncApplication  : Started AsyncApplication in 0.784 seconds (JVM running for 1.052)
2022-10-23 17:54:00.884  INFO 17644 --- [         task-1] m.l.s.async.service.HelloServiceImpl     : sayHello begins...
2022-10-23 17:54:00.884  INFO 17644 --- [         task-2] m.l.s.async.service.RateServiceImpl      : Lets try to get current rate...
2022-10-23 17:54:00.884  INFO 17644 --- [           main] mio68.lab.spring.async.AsyncApplication  : Current rate is: [null]
2022-10-23 17:54:00.888  INFO 17644 --- [           main] mio68.lab.spring.async.AsyncApplication  : Got rate future: [java.util.concurrent.CompletableFuture@37fbe4a8[Not completed]]
2022-10-23 17:54:00.888  INFO 17644 --- [         task-3] m.l.s.async.service.RateServiceImpl      : Lets try to get current rate for future using...
Hello World!
2022-10-23 17:54:05.885  INFO 17644 --- [         task-1] m.l.s.async.service.HelloServiceImpl     : sayHello finished.
2022-10-23 17:54:07.893  INFO 17644 --- [         task-3] m.l.s.async.service.RateServiceImpl      : Rate [31] successfully obtained for future using!%n
2022-10-23 17:54:07.894  INFO 17644 --- [           main] mio68.lab.spring.async.AsyncApplication  : Current rate(from future) is: [31]
Press [ENTER] to quit:
2022-10-23 17:54:10.893  INFO 17644 --- [         task-2] m.l.s.async.service.RateServiceImpl      : Rate [31] successfully obtained!
```

2. Async method that returns void is performed asynchronously quite well!


3. Async method that returns primitive value causes exception.

```   
   java.lang.IllegalStateException: Failed to execute ApplicationRunner
   at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:765)
   at org.springframework.boot.SpringApplication.callRunners(SpringApplication.java:752)
   at org.springframework.boot.SpringApplication.run(SpringApplication.java:315)
   at org.springframework.boot.SpringApplication.run(SpringApplication.java:1306)
   at org.springframework.boot.SpringApplication.run(SpringApplication.java:1295)
   at mio68.lab.spring.async.AsyncApplication.main(AsyncApplication.java:21)
   Caused by: org.springframework.aop.AopInvocationException: Null return value from advice does not match primitive return type for: public abstract int mio68.lab.spring.async.service.RateService.getSuppliersCounter() throws java.lang.InterruptedException
   at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:229)
   at jdk.proxy2/jdk.proxy2.$Proxy35.getSuppliersCounter(Unknown Source)
   at mio68.lab.spring.async.AsyncApplication.lambda$startupRunner3$2(AsyncApplication.java:49)
   at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:762)
   ... 5 more
```

4. How to use @Async with CompletableFuture?

There is a right way to use @Async with CompletableFuture. 
Just return CompletableFuture.completedFuture() from method annotated with @Async.
Look at the example:

```
   @Async
   public CompletableFuture<Integer> getRateFuture() throws InterruptedException {
       System.out.println("Lets try to get current rate for future using...");
       TimeUnit.SECONDS.sleep(7);
       int rate = 31;
       System.out.printf("Rate [%s] successfully obtained for future using!%n", rate);
       return CompletableFuture.completedFuture(rate);
   }
```

Console output:
```
2022-10-23 17:54:00.385  INFO 17644 --- [           main] mio68.lab.spring.async.AsyncApplication  : Starting AsyncApplication using Java 17 on LAPTOP-UNVDQTPM with PID 17644 (C:\Users\Igor\IdeaProjects\mio68-lab-spring\mio68-lab-spring-async\target\classes started by Igor in C:\Users\Igor\IdeaProjects\mio68-lab-spring)
2022-10-23 17:54:00.387  INFO 17644 --- [           main] mio68.lab.spring.async.AsyncApplication  : No active profile set, falling back to 1 default profile: "default"
2022-10-23 17:54:00.868  INFO 17644 --- [           main] mio68.lab.spring.async.AsyncApplication  : Started AsyncApplication in 0.784 seconds (JVM running for 1.052)
2022-10-23 17:54:00.884  INFO 17644 --- [         task-1] m.l.s.async.service.HelloServiceImpl     : sayHello begins...
2022-10-23 17:54:00.884  INFO 17644 --- [         task-2] m.l.s.async.service.RateServiceImpl      : Lets try to get current rate...
2022-10-23 17:54:00.884  INFO 17644 --- [           main] mio68.lab.spring.async.AsyncApplication  : Current rate is: [null]
2022-10-23 17:54:00.888  INFO 17644 --- [           main] mio68.lab.spring.async.AsyncApplication  : Got rate future: [java.util.concurrent.CompletableFuture@37fbe4a8[Not completed]]
2022-10-23 17:54:00.888  INFO 17644 --- [         task-3] m.l.s.async.service.RateServiceImpl      : Lets try to get current rate for future using...
Hello World!
2022-10-23 17:54:05.885  INFO 17644 --- [         task-1] m.l.s.async.service.HelloServiceImpl     : sayHello finished.
2022-10-23 17:54:07.893  INFO 17644 --- [         task-3] m.l.s.async.service.RateServiceImpl      : Rate [31] successfully obtained for future using!%n
2022-10-23 17:54:07.894  INFO 17644 --- [           main] mio68.lab.spring.async.AsyncApplication  : Current rate(from future) is: [31]
Press [ENTER] to quit:
2022-10-23 17:54:10.893  INFO 17644 --- [         task-2] m.l.s.async.service.RateServiceImpl      : Rate [31] successfully obtained!
```
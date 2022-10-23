1. Async method that is going to return Integer
Async method returns NULL to client and then executes asynchronously and never returns it's 
actual return value to client.

```
    @Override
    @Async
    public Integer getRate() throws InterruptedException {
```

Console output:
```
sayHello begin
Current rate is: null
Lets try to get current rate...
Press [ENTER] to quit:
Hello World!
sayHello finished
Rate [31] successfully obtained!
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

4. How to use @Async with CompletableFuture
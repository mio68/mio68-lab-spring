### Spring Web Security filters and custom filter creation

#### Links and sources
Good article! https://reflectoring.io/spring-security/

1. At start Spring will log DefaultSecurityFilterChain filters class names
```
2023-03-30 11:11:22.317  INFO 9456 --- [           main] o.s.s.web.DefaultSecurityFilterChain     : Will secure any request with [org.springframework.security.web.session.DisableEncodeUrlFilter@61a91c9b, org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter@17092fff, org.springframework.security.web.context.SecurityContextPersistenceFilter@76464795, org.springframework.security.web.header.HeaderWriterFilter@35a0e495, org.springframework.security.web.csrf.CsrfFilter@24d61e4, org.springframework.security.web.authentication.logout.LogoutFilter@65e0b505, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter@6af91cc8, org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter@3f183caa, org.springframework.security.web.authentication.ui.DefaultLogoutPageGeneratingFilter@2121d1f9, org.springframework.security.web.authentication.www.BasicAuthenticationFilter@f9f3928, org.springframework.security.web.savedrequest.RequestCacheAwareFilter@48f4713c, org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter@f1868c9, org.springframework.security.web.authentication.AnonymousAuthenticationFilter@31e2232f, org.springframework.security.web.session.SessionManagementFilter@4fe64d23, org.springframework.security.web.access.ExceptionTranslationFilter@37b56ac7, org.springframework.security.web.access.intercept.FilterSecurityInterceptor@696b4a95]
```

2. How to log security filters invocations?
You can see how filter chains work and how filters are invoked.
```
logging.level.org.springframework.security.web.FilterChainProxy=TRACE
```

3. Анализ создания цепочки фильтров

Включаем логирование создания бинов:
```
logging.level.org.springframework.beans=TRACE
```
Находим в журнале раздел от 
```
2023-03-31 12:08:32.827 DEBUG 29244 --- [           main] o.s.b.f.s.DefaultListableBeanFactory     : Creating shared instance of singleton bean 'securityFilterChainRegistration'
...
# Creation of a FilterChain
2023-03-31 11:09:40.604 DEBUG 17844 --- [           main] o.s.b.f.s.DefaultListableBeanFactory     : Creating shared instance of singleton bean 'filterChain'
# It's configuration
2023-03-31 12:08:32.959 TRACE 29244 --- [           main] o.s.b.f.s.DefaultListableBeanFactory     : Returning cached instance of singleton bean 'customSecurityConfig'
...
2023-03-31 12:08:33.135  INFO 29244 --- [           main] o.s.s.web.DefaultSecurityFilterChain     : Will secure any request with [org.springframework.security.web.session.DisableEncodeUrlFilter@5f0f9947, org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter@1aad0b1, org.springframework.security.web.context.SecurityContextPersistenceFilter@2819c460, org.springframework.security.web.header.HeaderWriterFilter@60783105, org.springframework.security.web.csrf.CsrfFilter@64984b0f, org.springframework.security.web.authentication.logout.LogoutFilter@277bf091, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter@6cff61fc, org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter@1f7076bc, org.springframework.security.web.authentication.ui.DefaultLogoutPageGeneratingFilter@c3edf4c, mio68.lab.srping.web.security.customfilter.filter.HeaderParsingFilter@5af8bb51, org.springframework.security.web.authentication.www.BasicAuthenticationFilter@6c3659be, org.springframework.security.web.savedrequest.RequestCacheAwareFilter@38bb9d7a, org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter@1e8ab90f, org.springframework.security.web.authentication.AnonymousAuthenticationFilter@799ed4e8, org.springframework.security.web.session.SessionManagementFilter@35d5ac51, org.springframework.security.web.access.ExceptionTranslationFilter@546e61d5, org.springframework.security.web.access.intercept.FilterSecurityInterceptor@30b131b2]
2023-03-31 11:09:40.780 TRACE 17844 --- [           main] o.s.b.f.s.DefaultListableBeanFactory     : Finished creating instance of bean 'filterChain'

...

# Binding filter chains WebSecurityConfiguration
2023-03-31 12:08:33.148 TRACE 29244 --- [           main] f.a.AutowiredAnnotationBeanPostProcessor : Autowiring by type from bean name 'org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration' to bean named 'filterChain'
2023-03-31 12:08:33.148 TRACE 29244 --- [           main] f.a.AutowiredAnnotationBeanPostProcessor : Autowiring by type from bean name 'org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration' to bean named 'anonymousFilterChain'

...
2023-03-31 12:08:33.159 TRACE 29244 --- [           main] o.s.b.f.s.DefaultListableBeanFactory     : Finished creating instance of bean 'org.springframework.security.config.annotation.web.configuration.WebMvcSecurityConfiguration'
2023-03-31 12:08:33.159 DEBUG 29244 --- [           main] o.s.b.f.s.DefaultListableBeanFactory     : Creating shared instance of singleton bean 'requestDataValueProcessor'
2023-03-31 12:08:33.159 TRACE 29244 --- [           main] o.s.b.f.s.DefaultListableBeanFactory     : Creating instance of bean 'requestDataValueProcessor'
2023-03-31 12:08:33.159 TRACE 29244 --- [           main] o.s.b.f.s.DefaultListableBeanFactory     : Returning cached instance of singleton bean 'org.springframework.security.config.annotation.web.configuration.WebMvcSecurityConfiguration'
2023-03-31 12:08:33.161 TRACE 29244 --- [           main] o.s.b.f.s.DefaultListableBeanFactory     : Finished creating instance of bean 'requestDataValueProcessor'
2023-03-31 12:08:33.162 TRACE 29244 --- [           main] o.s.b.f.s.DefaultListableBeanFactory     : Returning cached instance of singleton bean 'org.springframework.security.config.annotation.web.configuration.HttpSecurityConfiguration'
2023-03-31 12:08:33.162 TRACE 29244 --- [           main] o.s.b.f.s.DefaultListableBeanFactory     : Returning cached instance of singleton bean 'filterChain'
2023-03-31 12:08:33.162 TRACE 29244 --- [           main] o.s.b.f.s.DefaultListableBeanFactory     : Returning cached instance of singleton bean 'anonymousFilterChain'


```

4. Two filter chains are created 

Take a look at ```FreeContentConfig``` and ```CustomSecurityConfig```.
Pay attention to @Order.

Если одна из цепочек сработала, то вторая не будет? Да, именно так!
Поэтому цепочку для анонимного доступа надо размещать первой, а цепочку
которая RequestMatcher=any request - последней.

5. Анализ использования цепочек фильтров

Ищем в журнале сообщения от ```FilterChainProxy```
```
2023-03-31 12:20:07.424 TRACE 29244 --- [nio-8080-exec-9] o.s.security.web.FilterChainProxy        : Trying to match request against DefaultSecurityFilterChain [RequestMatcher=Ant [pattern='/api/v1/hi'], Filters=[org.springframework.security.web.session.DisableEncodeUrlFilter@70887727, org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter@56da7487, org.springframework.security.web.context.SecurityContextPersistenceFilter@1cd3b138, org.springframework.security.web.header.HeaderWriterFilter@121bb45b, org.springframework.security.web.csrf.CsrfFilter@726aa968, org.springframework.security.web.authentication.logout.LogoutFilter@a098d76, org.springframework.security.web.savedrequest.RequestCacheAwareFilter@151bf776, org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter@5a6d30e2, org.springframework.security.web.authentication.AnonymousAuthenticationFilter@599e4d41, org.springframework.security.web.session.SessionManagementFilter@4faa298, org.springframework.security.web.access.ExceptionTranslationFilter@15639440, org.springframework.security.web.access.intercept.FilterSecurityInterceptor@2b680207]] (1/2)
```

6. Формирование ответа
При переопределении метода OncePerRequestFilter::doFilterInternal если сгенерировать 
исключение AuthenticationException то вернется 401
но возникнет ERROR
```
2023-04-02 13:38:14.481 ERROR 23868 --- [nio-8080-exec-2] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception
javax.security.sasl.AuthenticationException: header [Content-Type] is null
```

Лучше формировать ответ программно и не вызывать последующие фильтры.
```
response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
response.setContentType("application/json");
response.getOutputStream().print("\"header [%s] is null\"".formatted(headerName));
```
Еще правильнее имплементировать и использовать AuthenticationFailureHandler.

8. OncePerRequestFilter
Если создан такой @Bean то он обязательно вызывается: 
- в цепочке фильтров SecurityFilterChain в которую включен,
- если отработала цепочка SecurityFilterChain и он не был вызван в ней, то будет вызван отдельно, после цепочки, когда
  FilterChainProxy напишет -   : Secured GET ...


9. 


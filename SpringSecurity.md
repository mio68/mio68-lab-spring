Кратко про создание в Spring кастомных фильтров и кастомных провайдеров

1. Фильтр - достает из запроса объект Authentication, валидирует его и передает для авторизации и заполнения деталей провайдерам авторизации.
   При любых проблемах бросаем AuthenticatoinException - цепочка аутентификации будет прервана и будет вызван FailureHandler

abstract class MyFilter extends AbstractAuthenticationProcessingFilter {

// проверяем приложим ли данный фильтр к запросу
protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
return isApplicabelFor(request);
}


// Основная работа здесь.
@Override
public Authentication attemptAuthentication(HttpServletRequest rq, HttpServletResponse res) throws AuthenticatoinException {
Authentication auth = createAuthentication(rq, res);
getAuthenticationManager().authenticate(authenticaton);
}

// Если надо доп действия после успешной авторизации

@Override
protected void successfulAuthentication() Х
super.successfulAuthentication(request, response, chain, authResult);
//  что то делаем еще
chain.doFilter(request, response);
}

}


2. Обработка ошибок

public class MyFailureHandler implements AuthenticationFailureHandler {
public void onAuthenticationFailure(
HttpServletRequest httpServletRequest,
HttpServletResponse httpServletResponse,
AuthenticationException e) {

// Формируем 401 с подробностями

}

}

3. обработчик успешной авторизации для REST не должен перенаправлять на страницу входа в приложение поэтому используй такой
   SimpleUrlAuthenticationSuccessHandler simpleUrlAuthenticationSuccessHandler = new SimpleUrlAuthenticationSuccessHandler();
   simpleUrlAuthenticationSuccessHandler.setRedirectStrategy(new NoRedirectStrategy());

4. Создание фильтра
   // Устанавливаем
   filter.setAuthenticationManager(authenticationManager);
   filter.setAuthenticationSuccessHandler(getSuccessHandler());
   filter.setAuthenticationFailureHandler(getFailureHandler());

5. добавление фильтров
   public class WebMvcConfiguration extends WebSecurityConfigurerAdapter {

@Override
protected void configure(HttpSecurity httpSecurity) throws Exception {
// ...
httpSecurity.addFilterBefore(myFilterA, AnonymousAuthenticationFilter.class);
httpSecurity.addFilterBefore(myFilterB, AnonymousAuthenticationFilter.class);
// ...
}

}

6. Фильтр создает объект аутентификации, заполняет поля и передает его провайдеру аутентификации
   class MyAuthenticationToken extends AbstractAuthenticationToken {

}

7. Провайдер аутентификаци - проверяет что пользователь существует, не заблокирован, проверяет креды, заполняет гранты, заполняет детали пользователя.

class MyAuthenticationProvider implements AuthenticationProvider {

// проверяет может ли он оработать аутентификацию
public boolean supports(Class<?> aClass) {
return MyAuthenticationToken.class.isAssignableFrom(aClass);
}

// выполняет аутентификацию, при проблемах бросает AuthenticationException
public Authentication authenticate(Authentication auth) throws AuthenticationException {
(MyAuthenticationToken) auth;
...
}

8. Регистрация провайдеров
   public class WebMvcConfiguration extends WebSecurityConfigurerAdapter {

@Override
protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
authenticationManagerBuilder.authenticationProvider(myAuthenticationProviderA);
authenticationManagerBuilder.authenticationProvider(myAuthenticationProviderB);
}

}

}
package mio68.lab.spring.webapp.secureapp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class PersonServiceSecurityConfig extends WebSecurityConfigurerAdapter {

//    Setup in-memory users.
//    @Override
//    protected void configure(AuthenticationManagerBuilder authBuilder)
//            throws Exception {
//
//        UserDetails adminUser = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("admin")
//                .authorities("ADMIN", "USER").build();
//        UserDetails normalUser = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("user")
//                .authorities("USER").build();
//
//        authBuilder.inMemoryAuthentication()
//                .withUser(adminUser)
//                .withUser(normalUser);
//    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.securityContext()
                .and()
                .exceptionHandling()
                .and()
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/person")
                .hasRole("ADMIN");
    }

}

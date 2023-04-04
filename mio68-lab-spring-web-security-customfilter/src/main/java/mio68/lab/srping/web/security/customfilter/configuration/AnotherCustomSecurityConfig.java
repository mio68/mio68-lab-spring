package mio68.lab.srping.web.security.customfilter.configuration;

import mio68.lab.srping.web.security.customfilter.filter.HeaderParsingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class AnotherCustomSecurityConfig {

    @Bean
    @Order(3)
    public SecurityFilterChain anotherFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(request -> request.anyRequest().authenticated());
        http.addFilterBefore(
                new HeaderParsingFilter(),
                AnonymousAuthenticationFilter.class);

        return http.build();
    }


}

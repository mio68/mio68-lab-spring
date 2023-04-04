package mio68.lab.srping.web.security.customfilter.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class FreeContentConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain anonymousFilterChain(HttpSecurity http) throws Exception {
        http.antMatcher("/api/v1/hi")
                .authorizeRequests()
                .antMatchers("/**")
                .permitAll();
        return http.build();
    }

}

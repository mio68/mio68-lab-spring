package mio68.lab.srping.web.security.customfilter.configuration;

import mio68.lab.srping.web.security.customfilter.filter.HeaderParsingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class CustomSecurityConfig {

    // This registers custom filter normally within DefaultSecurityFilterChain
    @Bean
    @Order(2)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(request -> request.anyRequest().authenticated());
        http.formLogin();
        http.httpBasic();
        http.addFilterBefore(
//                headerParsingFilter(),
                new HeaderParsingFilter(),
                BasicAuthenticationFilter.class);

        return http.build();
    }

//  This doesn't include custom filter to default filter chain
//  But filter will be within a SecurityFilterChain or outside any of SecurityFilterChains
//  if no one is matched to request!
//    @Bean
//    public HeaderParsingFilter headerParsingFilter() {
//        return new HeaderParsingFilter();
//    }


//   This configuration doesn't configure basic auth.
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.addFilterBefore(
//                new HeaderParsingFilter(),
//                BasicAuthenticationFilter.class);
//        return http.build();
//    }
}

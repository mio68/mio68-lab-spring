package mio68.lab.srping.web.security.customfilter.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

@Slf4j
@Component
@Order(LOWEST_PRECEDENCE-3)
public class PrintHeaderFilter extends OncePerRequestFilter {

    private final String headerName = "Content-Type";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("invoked");

        String header = request.getHeader(headerName);
        log.info("{} =  {}", headerName, header);

        filterChain.doFilter(request, response);
    }
}

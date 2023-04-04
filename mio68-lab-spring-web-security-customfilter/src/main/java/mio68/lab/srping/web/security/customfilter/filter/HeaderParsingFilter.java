package mio68.lab.srping.web.security.customfilter.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class HeaderParsingFilter extends OncePerRequestFilter {

    private final String headerName = "Content-Type";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException,
            IOException {

        log.info("invoked");

        String header = request.getHeader(headerName);
        log.info("{} =  {}", headerName, header);

//        It produces ERROR
//        2023-04-02 13:38:14.481 ERROR 23868 --- [nio-8080-exec-2] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception
//        javax.security.sasl.AuthenticationException: header [Content-Type] is null
//        if (header == null) {
//            throw new AuthenticationException("header [%s] is null".formatted(headerName));
//        }

        if (header == null) {
              response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
              response.setContentType("application/json");
              response.getOutputStream().print("\"header [%s] is null\"".formatted(headerName));

              // dont' invoke filterChain!
//               filterChain.doFilter(request, response);
        }
        else {
            filterChain.doFilter(request, response);
        }
//        filterChain.doFilter(request, response);
    }
}

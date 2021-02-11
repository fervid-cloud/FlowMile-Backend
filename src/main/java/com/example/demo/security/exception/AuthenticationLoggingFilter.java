package com.example.demo.security.exception;


import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


/**
 * In this case, this filter comes at first, but by default if no precedence is given, it goes at the last
 * i.e Ordered.LOWEST_PRECEDENCE(which has Integer.MAX_VALUE)
 * Objects that have the same order value will be sorted with arbitrary ordering with respect to other objects
 * with the same order value
 */

@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthenticationLoggingFilter implements Filter {


    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        log.info("-----------------------------------------------------------------The AuthenticationLogging Filter is called");
        System.out.println("--------------------------------The request came to AuthenticationLogging Filter");
  /*      HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestId = httpRequest.getHeader("Request-Id");
        log.info("The request id is : {}", requestId);
        UsernamePasswordAuthenticationToken authenticationDetail = convert((HttpServletRequest) request);
        log.info("The authentication detail is : {}", authenticationDetail);*/
        Util.showValueStatus((HttpServletRequest) request, this);
        chain.doFilter(request, response);

    }



    public UsernamePasswordAuthenticationToken convert(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);
        if (header == null) {
            return null;
        }

        header = header.trim();
        if (!StringUtils.startsWithIgnoreCase(header, "Basic")) {
            return null;
        }

        if (header.equalsIgnoreCase("Basic")) {
            throw new BadCredentialsException("Empty basic authentication token");
        }

        byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
        byte[] decoded;
        try {
            decoded = Base64.getDecoder().decode(base64Token);
        }
        catch (IllegalArgumentException e) {
            throw new BadCredentialsException(
                "Failed to decode basic authentication token");
        }

        String token = new String(decoded, StandardCharsets.UTF_8);

        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        UsernamePasswordAuthenticationToken result  = new UsernamePasswordAuthenticationToken(token.substring(0, delim), token.substring(delim + 1));
        result.setDetails(this.authenticationDetailsSource.buildDetails(request));
        return result;
    }

}

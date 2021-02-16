package com.example.demo.security;

import com.example.demo.utilities.Util;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/*
It will automatically be added to filter chain by spring once it has been made component
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class AuthenticationFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        System.out.println("reached at the filter chain ------------------------------------------------------------");
        System.out.println("----------------------------------------------------------------------------------------");
        Util.showValueStatus((HttpServletRequest) request, this);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}

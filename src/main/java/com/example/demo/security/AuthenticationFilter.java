package com.example.demo.security;

import com.example.demo.security.exception.Util;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
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

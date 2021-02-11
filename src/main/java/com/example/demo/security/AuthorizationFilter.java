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

@Component
@Order(3)
public class AuthorizationFilter implements Filter {

//    @Autowired
//    private InMemoryDatabase inMemoryDatabase;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        String requestUrl = getUrl(request);
        String typeOfRequest = getRequestType(request);

        Util.showValueStatus((HttpServletRequest) request, this);
        chain.doFilter(request, response);
        System.out.println("response is going");
    }

    private String getRequestType(ServletRequest request) {
        if(request instanceof HttpServletRequest) {
            String type = ((HttpServletRequest)request).getMethod();
            System.out.println("Request type is " + type);
            return type;
        }
        return null;
    }

    private String getUrl(ServletRequest request) {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = ((HttpServletRequest)request);
            String url = httpServletRequest.getRequestURL().toString();
            String testUrl = httpServletRequest.getRequestURI();
            System.out.println("Test url is :----------------------" + testUrl);
            String testUrl2 = httpServletRequest.getServletPath().toString();
            System.out.println("Test2 Url is : ---------------------" + testUrl2);

//            String queryString = ((HttpServletRequest)request).getQueryString();
            System.out.println("Request url is : " + url);
            return url;
        }
        return null;
    }

    @Override
    public void destroy() {
    }

}

package com.example.demo.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.filter.OncePerRequestFilter;

public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        try {

        }
         catch (AuthenticationException exception) {
             onUnSuccessfulAuthentication(request, response, exception);
         }
    }

    private void onUnSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        customAuthenticationEntryPoint.commence(request, response, exception);
    }

}

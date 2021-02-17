package com.example.demo.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;


public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    public CustomAuthenticationFailureHandler(
        CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException exception) throws IOException, ServletException {
        customAuthenticationEntryPoint.commence(request, response, exception);
    }
}

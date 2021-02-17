package com.example.demo.security;

import com.example.demo.exception.ExceptionResponseModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public CustomAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException {
        System.out.println("------------------------ UnAuthenticated User ---------------------------------------------------------------");
        String curTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy, HH:mm:ss a"));
        System.out.println("Current time is :-----------------------------" + curTime);
        response.addHeader("WWW-Authenticate", "Custom realm");
        ExceptionResponseModel unAuthenticatedResponse = ExceptionResponseModel.builder()
                                                             .error("UnAuthorized")
                                                             .message("Incorrect username or password")
                                                             .httpStatus(HttpStatus.UNAUTHORIZED)
                                                             .time(curTime)
                                                             .build();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        objectMapper.writeValue(response.getWriter(), unAuthenticatedResponse);
    }


}


package com.mss.polyflow.shared.security;

import com.mss.polyflow.shared.exception.ExceptionResponseModel;
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


/***
 * If you are not using BasicAuthenticationFilter or AbstractAuthenticationFilter and are using your own custom
 * filter for authentication without providing any AuthenticationEntryPoint and you are thinking like I did that
 * unauthenticated user will be automatically be handled by spring security through ExeptionTranslatorFilter,
 * then you are going to be frustrated like me. here is the updated link of official documentation of current version mentioning this clearly authenticationEntryPont
 *
 * If no authenticationEntryPoint(AuthenticationEntryPoint) is specified,
 *  then defaultAuthenticationEntryPointFor(AuthenticationEntryPoint, RequestMatcher) will be used.
 *
 *  The first AuthenticationEntryPoint will be used as the default if no matches were found.
 *
 *  If that is not provided defaults to Http403ForbiddenEntryPoint.
 *
 *  So basically you have to create you own authentication entry point if you have not used already provided
 *  authentication filter by spring
 *  so we can skip the entry point or access denied handler for invalid authorization as it is already
 *  provided by the spring security ExceptionTranslatorFilter(proof if we remove this entry point, then even for authentication exception
 *  we will get access denied response(unless we manually catch authentication exception and return the response by ourself, instead of letting spring handling it)
 *
 */
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


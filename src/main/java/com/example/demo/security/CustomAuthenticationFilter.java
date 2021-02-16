package com.example.demo.security;

import com.example.demo.service.UserService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


public class CustomAuthenticationFilter extends OncePerRequestFilter {

    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";

    public final JWTManager jwtManager;

    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    private final UserService userService;

    public CustomAuthenticationFilter(
        JWTManager jwtManager,
        CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
        UserService userService) {
        this.jwtManager = jwtManager;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        try {
                String authorizationHeaderValue = request.getHeader(AUTHORIZATION_HEADER);
            if (authorizationHeaderValue == null || !authorizationHeaderValue.startsWith(BEARER_PREFIX)) {
                filterChain.doFilter(request, response);
                return;
            }
            String jwtToken = authorizationHeaderValue.substring(BEARER_PREFIX.length());
            CustomAuthenticationToken customAuthenticationToken = jwtManager.verifyToken(jwtToken);
            if(customAuthenticationToken != null) {
                AccessInfo accessInfo = userService.getAccessInfo(customAuthenticationToken.getUsername());
                customAuthenticationToken.setAccessInfo(accessInfo);
                SecurityContextHolder.getContext().setAuthentication(customAuthenticationToken);
            }

            filterChain.doFilter(request, response);
        }
         catch (AuthenticationException exception) {
             SecurityContextHolder.clearContext();
             onUnSuccessfulAuthentication(request, response, exception);
         }
    }

    private void onUnSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
        throws IOException {
        customAuthenticationEntryPoint.commence(request, response, exception);
    }

}

package com.example.demo.security;

import com.example.demo.service.UserService;
import com.example.demo.utilities.AuthenticationLoggingFilter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Order(4)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final InMemoryDatabase inMemoryDatabase;
    private final AuthorizationFilter authorizationFilter;

    private final AuthenticationLoggingFilter authenticationLoggingFilter;

    private final JWTManager jwtManager;

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    private final UserService userService;

    public SecurityConfig(InMemoryDatabase inMemoryDatabase,
        AuthorizationFilter authorizationFilter,
        AuthenticationLoggingFilter authenticationLoggingFilter,
        JWTManager jwtManager,
        CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
        UserService userService) {
        this.inMemoryDatabase = inMemoryDatabase;
        this.authorizationFilter = authorizationFilter;
        this.authenticationLoggingFilter = authenticationLoggingFilter;
        this.jwtManager = jwtManager;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.userService = userService;
    }


    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();

        Map<Object, Object> userMapping = inMemoryDatabase.userPermissionMapping;
        for(Object user : userMapping.keySet()) {
            String givenUser = (String) user;
            UserDetails currentUser = User.withUsername(givenUser)
                                            .password(passwordEncoder().encode("12345")) // this needs to be done as spring has to know the hashing mechanism used, so password will of the form "{bycrpt}somePasswordHash"
                                            .authorities((List<SimpleGrantedAuthority>)userMapping.get(user))
                                            .build();
            inMemoryUserDetailsManager.createUser(currentUser);
        }

        return inMemoryUserDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
            .authorizeRequests()
                .mvcMatchers(new String[]{"/**/ping/**", "/h2-console/**", "/login/**"}).permitAll()
            .and()
//            .formLogin()
//            .and()
            .authorizeRequests()
                .anyRequest().authenticated();
//        http.addFilterAfter(authenticationLoggingFilter, BasicAuthenticationFilter.class);
//            http.addFilterBefore(AuthorizationFilter, UsernamePasswordAuthenticationFilter)
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(jwtManager, customAuthenticationEntryPoint, userService);
        http.addFilterBefore(customAuthenticationFilter, BasicAuthenticationFilter.class);

/*        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler());*/

//        http.httpBasic();
        http.requestCache().disable();

    }

    /**
     * AuthenticationEntryPoint implementation is there for 401 (unauthenticated user)
     */
    private AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                System.out.println("AuthenticationEntry Point called as user is not authenticated");
                httpServletResponse.setContentType("application/json");
                httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
//                PrintWriter out = httpServletResponse.getWriter();
//                out.append(response.toString());
//                {
//                    "timestamp": "2021-02-11T10:57:39.818+00:00",
//                                     "status": 401,
//                                                   "error": "Unauthorized",
//                                                                "message": "",
//                                                                               "path": "/test/firstRequest"
//                }

                JSONObject jsonResponse = new JSONObject();
                DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                String timestamp = LocalDateTime.now().format(formatter);
                jsonResponse.put("timestamp", timestamp);
                jsonResponse.put("error", "Unauthorized");
                jsonResponse.put("message", "You are not authenticated, please first login to the system");
                jsonResponse.put("status", HttpStatus.UNAUTHORIZED.value());
                jsonResponse.write(httpServletResponse.getWriter());

//                httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
//                httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), response.toString());

                System.out.println("User is not authenticated");
//                  httpServletResponse.sendRedirect("http://localhost:4000");
//                httpServletResponse.getWriter().append(response.toString());


            }
        };
    }

    /**
     * AccessDeniedHandler implementation is there for 403, FORBIDDEN access.(authenticated but not authorized user)
     */
    private AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
                System.out.println("AccessDeniedHandler called as user is not authorized");
                httpServletResponse.setContentType("application/json");
                httpServletResponse.setStatus(403);

                JSONObject jsonResponse = new JSONObject();
                DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                String timestamp = LocalDateTime.now().format(formatter);
                jsonResponse.put("timestamp", timestamp);
                jsonResponse.put("error", "Forbidden");
                jsonResponse.put("message", "You are forbidden to access this resource");
                jsonResponse.put("status", HttpStatus.FORBIDDEN.value());
                jsonResponse.write(httpServletResponse.getWriter());

//                httpServletResponse.getWriter().append("Access denied");

            }
        };
    }


    @Bean
    WebMvcConfigurer webMvcConfigurer() {

        /**
         * method to configure CORS globally
         * The reason to specify a maxAge of 0 is so that the browser doesn't cache any
         * CORS preflight responses while we are making changes throughout the project.
         */
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
//					.maxAge(0)
                    .allowedOrigins("http://localhost:3300")
//                    .allowedMethods("HEAD")
                    .allowedHeaders("Authorization");
            }
        };
    }

}
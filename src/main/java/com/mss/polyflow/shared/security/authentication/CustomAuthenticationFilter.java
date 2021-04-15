package com.mss.polyflow.shared.security.authentication;

import com.mss.polyflow.shared.security.authorization.AccessInfo;
import com.mss.polyflow.shared.security.authorization.CustomAuthenticationToken;
import com.mss.polyflow.shared.security.token.JWTManager;
import com.mss.polyflow.shared.service.UserService;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Slf4j
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

            String authorizationHeaderValue = request.getHeader(AUTHORIZATION_HEADER);
            if (authorizationHeaderValue == null || !authorizationHeaderValue.startsWith(BEARER_PREFIX)) {
                filterChain.doFilter(request, response);
                return;
            }

            String jwtToken = authorizationHeaderValue.substring(BEARER_PREFIX.length());
            String username = jwtManager.verifyAndGetUsername(jwtToken);
            System.out.println("username is : " + username);

            if(username != null) {
                CurrentUserDetail currentUserDetail = userService.getUserDetailForAuthentication(username);

                if(currentUserDetail != null) {
                    log.info("User Info is : {}", currentUserDetail);

                    AccessInfo accessInfo = new AccessInfo();
                    CustomAuthenticationToken customAuthenticationToken = new CustomAuthenticationToken(accessInfo.getAuthorities().stream().map(
                            SimpleGrantedAuthority::new).collect(Collectors.toList()));

                    customAuthenticationToken.setDetails(currentUserDetail);
                    customAuthenticationToken.setUsername(username);
                    customAuthenticationToken.setAccessInfo(accessInfo);
                    customAuthenticationToken.setAuthenticated(true);

                    SecurityContextHolder.getContext().setAuthentication(customAuthenticationToken);

                    log.info("user has been authenticated");
                    log.info("current authenticated user authentication token is {}", customAuthenticationToken);
                }
            }

            log.info("authentication filter done, going further in filter chain");
            filterChain.doFilter(request, response);
/*        }
         catch (AuthenticationException exception) {
             log.info("Invalid user ---------------------------");
             SecurityContextHolder.clearContext();
             onUnSuccessfulAuthentication(request, response, exception);
         }*/
    }


    private void onUnSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
        throws IOException {
        customAuthenticationEntryPoint.commence(request, response, exception);
    }


    /**
     * Note- have added this api for api testing through springdoc-openapi, it is not needed, but
     * again it teaches how to use MvcRequestMapping if in future it is needed for some use cases
     *
     * Can be overridden in subclasses for custom filtering control,
     * returning {@code true} to avoid filtering of the given request.
     * <p>The default implementation always returns {@code false}.
     * @param request current HTTP request
     * @return whether the given request should <i>not</i> be filtered
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        System.out.println("The servlet path is : " + request.getServletPath());
        return doesRequestMatch(request);
    }


    /***
     * example of using MVCRequestMatcher
     * @Test
     * public void testMatcher() {
     *     MvcRequestMatcher mvcRequestMatcher = new MvcRequestMatcher(new HandlerMappingIntrospector(), "/users/{id}");
     *     System.out.println(mvcRequestMatcher.matches(new MockHttpServletRequest("GET", "/users/1")));
     *     System.out.println(mvcRequestMatcher.matches(new MockHttpServletRequest("GET", "/users/aaa")));
     *     System.out.println(mvcRequestMatcher.matches(new MockHttpServletRequest("GET", "/users")));
     *     System.out.println(mvcRequestMatcher.matches(new MockHttpServletRequest("POST", "/users/1")));
     *     System.out.println(mvcRequestMatcher.matches(new MockHttpServletRequest("PUT", "/users/1")));
     *     System.out.println(mvcRequestMatcher.matches(new MockHttpServletRequest("DELETE", "/users/1")));
     * }
     * @param request
     * @return
     */
    private boolean doesRequestMatch(HttpServletRequest request) {
        HandlerMappingIntrospector handlerMappingIntrospector = new HandlerMappingIntrospector();
        MvcRequestMatcher mvcRequestMatcher = new MvcRequestMatcher(handlerMappingIntrospector, "/login/**");
        mvcRequestMatcher.setMethod(HttpMethod.POST);
        return mvcRequestMatcher.matches(request);
    }

}

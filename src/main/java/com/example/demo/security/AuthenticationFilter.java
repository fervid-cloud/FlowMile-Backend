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

/***
 * Spring Security is installed as a single Filter in the chain, and its concerete type is FilterChainProxy, for reasons that will become apparent soon. In a Spring Boot app the security filter is a @Bean in the ApplicationContext, and it is installed by default so that it is applied to every request.
 *
 * There can be multiple filter chains all managed by Spring Security in the same top level FilterChainProxy and all unknown to the container. The Spring Security filter contains a list of filter chains, and dispatches a request to the first chain that matches it.
 *
 * Note also that:
 *     The fact that all filters internal to Spring Security are unknown to the container is important, especially in a Spring Boot application, where all @Beans of type Filter are registered automatically with the container by default. So if you want to add a custom filter to the security chain, you need to either not make it a @Bean or wrap it in a FilterRegistrationBean that explicitly disables the container registration.
 * So, when you define a filter as a Spring bean, it is registered with the servlet container automatically, but not with the Spring Security filter chain. That is why you need to add it to the Spring Security chain explicitly using addFilter method. You also need to disable auto-registration in the servlet container or the filter will be called twice.
 *
 *
 * https://docs.spring.io/spring-boot/docs/2.0.0.RELEASE/reference/htmlsingle/#howto-add-a-servlet-filter-or-listener
 *
 * Like any other Spring bean, you can define the order of Servlet filter beans; please make sure to check the “the section called “Registering Servlets, Filters, and Listeners as Spring Beans”” section.
 *
 * Disable Registration of a Servlet or Filter
 *
 * As described earlier, any Servlet or Filter beans are registered with the servlet container automatically. To disable registration of a particular Filter or Servlet bean, create a registration bean for it and mark it as disabled, as shown in the following example:
 *
 * @Bean
 * public FilterRegistrationBean registration(MyFilter filter) {
 * 	FilterRegistrationBean registration = new FilterRegistrationBean(filter);
 * 	registration.setEnabled(false);
 * 	return registration;
 * }
 */

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

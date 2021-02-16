package com.example.demo.security;

import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final Logger log = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder delegatingPasswordEncoder;

    public CustomAuthenticationProvider(
        UserDetailsService userDetailsService,
        PasswordEncoder delegatingPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.delegatingPasswordEncoder = delegatingPasswordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {
        log.info("--------------------------------------------------------In CustomAuthenticationProvider, going to match password");

        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        System.out.println("--------------------------------------------------------------------");
        System.out.println("User Details I : " + userDetails);
        System.out.println(String.format("The actual password for the given password %s is: %s " , password, delegatingPasswordEncoder.encode(password)));
        System.out.println("_--------------------------------------------------------------------");
        if (!delegatingPasswordEncoder.matches(password, userDetails.getPassword())) {
            log.info("-------------------------password did not match");
            return null;
        }

        final Collection<? extends GrantedAuthority> grantedAuthorities = userDetails.getAuthorities();
        Object principal = username;
        Object credentials = null;
        final Authentication responseAuthenticationObj = new UsernamePasswordAuthenticationToken(
            principal, credentials, grantedAuthorities);
        log.info("--------------------------------password matches");
        return responseAuthenticationObj;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

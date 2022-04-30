/*
package com.java.expensetracker.security.jwt;

import com.java.expensetracker.security.service.UserDetailsImplementation;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    public JwtTokenProvider jwtTokenProvider;
    private UserDetailsImplementation userDetailsImplementation;

    public JwtTokenConfigurer(JwtTokenProvider jwtTokenProvider, UserDetailsImplementation userDetailsImplementation) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsImplementation = userDetailsImplementation;
    }

    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.addFilterBefore(new JwtTokenFilter(jwtTokenProvider, userDetailsImplementation), UsernamePasswordAuthenticationFilter.class);
    }
 }
*/

package com.java.expensetracker.security;

import com.java.expensetracker.security.jwt.JwtTokenProvider;
import com.java.expensetracker.security.service.UserDetailsImplementation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private UserDetailsImplementation userDetailsImplementation;
    private JwtTokenProvider jwtTokenProvider;

    public WebSecurityConfig(UserDetailsImplementation userDetailsImplementation, JwtTokenProvider jwtTokenProvider) {
        this.userDetailsImplementation = userDetailsImplementation;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsImplementation).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }


    //TODO pattern check
    //.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().antMatchers("/api/auth/signin").permitAll().anyRequest().authenticated();

        //httpSecurity.apply(new JwtTokenConfigurer(jwtTokenProvider, userDetailsImplementation));

    }
}

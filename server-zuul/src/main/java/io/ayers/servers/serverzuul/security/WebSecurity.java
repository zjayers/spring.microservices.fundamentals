package io.ayers.servers.serverzuul.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity
        extends WebSecurityConfigurerAdapter {

    private final Environment environment;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, environment.getProperty("api.accounts.login-path")).permitAll()
                .antMatchers(HttpMethod.POST, environment.getProperty("api.accounts.registration-path")).permitAll()
                .antMatchers(environment.getProperty("api.accounts.h2-console-path")).permitAll()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated() // Any other HTTP Requests must be authenticated
                .and()
                .addFilter(new AuthorizationFilter(authenticationManager(), environment));

        // Keep Session from cacheing - keeping the headers from being cached and force reauthorization of JWT token
        // during each request
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}

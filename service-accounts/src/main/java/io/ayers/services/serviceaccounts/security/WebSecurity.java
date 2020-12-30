package io.ayers.services.serviceaccounts.security;

import io.ayers.services.serviceaccounts.services.interfaces.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity
        extends WebSecurityConfigurerAdapter {

    private final Environment environment;
    private final AccountService accountService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable();

        String gateway = environment.getRequiredProperty("gateway.ip");

        http.authorizeRequests()
                .antMatchers("/**")
                .hasIpAddress(gateway)
                .and()
                .addFilter(getAuthenticationFilter());

        http.headers().frameOptions().disable();
    }

    private Filter getAuthenticationFilter() throws Exception {
        AuthorizationFilter authorizationFilter = new AuthorizationFilter(environment, authenticationManager(),
                accountService);
        // Authentication path will be "/login"
        // authenticationFilter.setAuthenticationManager(authenticationManager());
        // Set a custom login path
        authorizationFilter.setFilterProcessesUrl(environment.getRequiredProperty("login.url.path"));
        return authorizationFilter;
    }
}

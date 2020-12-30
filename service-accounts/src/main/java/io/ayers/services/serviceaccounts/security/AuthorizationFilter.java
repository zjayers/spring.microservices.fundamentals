package io.ayers.services.serviceaccounts.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ayers.services.serviceaccounts.models.dto.AccountDto;
import io.ayers.services.serviceaccounts.models.request.LoginAccountRequestModel;
import io.ayers.services.serviceaccounts.services.interfaces.AccountService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthorizationFilter extends UsernamePasswordAuthenticationFilter {

    private final AccountService accountService;
    private final String jwtExpiration;
    private final String jwtSecret;

    public AuthorizationFilter(Environment environment,
                               AuthenticationManager authenticationManager,
                               AccountService accountService) {
        this.accountService = accountService;
        jwtExpiration = environment.getRequiredProperty("authorization.token.expiration-time");
        jwtSecret = environment.getRequiredProperty("authorization.token.jwt-secret");
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        LoginAccountRequestModel credentials;

        try {
            credentials = new ObjectMapper().readValue(request.getInputStream(), LoginAccountRequestModel.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials
                        .getPassword(), new ArrayList<>()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) {

        // Get the users email (username)
        String email = ((User) authResult.getPrincipal()).getUsername();

        // Get the user information DTO using the email (username)
        AccountDto userDto = accountService.getAccountDetailsByEmail(email);

        // Setup JWT Token
        String jwtToken =
                Jwts.builder()
                        .setSubject(userDto.getUserId())
                        .setExpiration(new Date(System.currentTimeMillis() + Long
                                .parseLong(jwtExpiration)))
                        .signWith(SignatureAlgorithm.HS512, jwtSecret)
                        .compact();

        response.addHeader("token", jwtToken);
        response.addHeader("userId", userDto.getUserId());
    }
}

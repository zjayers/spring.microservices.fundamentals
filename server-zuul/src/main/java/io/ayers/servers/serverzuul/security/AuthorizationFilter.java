package io.ayers.servers.serverzuul.security;

import io.jsonwebtoken.Jwts;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthorizationFilter
        extends BasicAuthenticationFilter {
    private final String jwtHeaderName;
    private final String jwtHeaderPrefix;
    private final String jwtHeaderSecret;

    public AuthorizationFilter(AuthenticationManager authenticationManager,
                               Environment environment) {
        super(authenticationManager);

        this.jwtHeaderName = environment.getProperty("authorization.token.name", "Authorization");
        this.jwtHeaderPrefix = environment.getProperty("authorization.token.prefix", "Bearer");
        this.jwtHeaderSecret = environment.getProperty("authorization.token.jwt-secret", "InvalidJwtKey");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String authorizationHeader = request.getHeader(jwtHeaderName);

        if (authorizationHeader == null || !authorizationHeader.startsWith(jwtHeaderPrefix)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(request);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(jwtHeaderName);

        if (authorizationHeader == null) {
            return null;
        }

        String token = authorizationHeader.replace(jwtHeaderPrefix, "");
        String userId =
                Jwts.parser()
                        .setSigningKey(jwtHeaderSecret)
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject();

        if (userId == null) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
    }
}

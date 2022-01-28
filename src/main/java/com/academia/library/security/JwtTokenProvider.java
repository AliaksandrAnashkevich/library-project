package com.academia.library.security;

import com.academia.library.exception.JwtTokenException;
import com.academia.library.model.Authority;
import com.academia.library.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
@PropertySource("classpath:security.yml")
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key:c2VjcmV0LWtleQ==}")
    private String jwtSecret;

    @Value("${security.jwt.token.bearer:Bearer }")
    private String bearer;

    @Value("${security.jwt.token.expire-length:300000}")
    private long validityInMilliseconds;

    private final JwtUserDetailsService jwtUserDetailsService;

    public String createToken(String username, Set<Role> roles) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("auth", this.getRoleNames(roles));

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith(bearer)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtTokenException();
        }
    }

    public Set<String> getRoleNames(Collection<Role> userRoles) {
        Set<String> rolesNames = new HashSet<>();
        if (userRoles == null) {
            return rolesNames;
        }
        userRoles.forEach((role) -> {
            Set<Authority> permissions = role.getAuthorities();
            permissions.forEach((authority) -> rolesNames.add(authority.getName()));
            rolesNames.add(role.getName());
        });
        return rolesNames;
    }
}

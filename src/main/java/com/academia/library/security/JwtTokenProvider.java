package com.academia.library.security;


import com.academia.library.model.Authority;
import com.academia.library.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("$(jwt.secret)")
    private String jwtSecret;

    public String generateToken(String email, Set<Role> roles) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", this.getRoleNames(roles));
        Date date = Date.from(LocalDate.now().plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error("invalid token");
        }
        return false;
    }

    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
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

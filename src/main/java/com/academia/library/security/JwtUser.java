package com.academia.library.security;

import com.academia.library.model.Authority;
import com.academia.library.model.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
public class JwtUser implements UserDetails {

    private boolean enabled;
    private String username;
    private String password;
    private Set<Role> roles;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new HashSet<>();
        Set<Authority> authoritySet = new HashSet<>();
        if (roles == null) {
            return authorities;
        }
        roles.forEach((role) -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            authoritySet.addAll(role.getAuthorities());
        });
        authoritySet.forEach((authority) -> {
            authorities.add(new SimpleGrantedAuthority(authority.getName()));
        });
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}

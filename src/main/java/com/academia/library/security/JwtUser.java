package com.academia.library.security;

import com.academia.library.model.Authority;
import com.academia.library.model.Role;
import com.academia.library.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class JwtUser extends User implements UserDetails {

    private final boolean enabled;

    public JwtUser(
            Long id,
            String firstName,
            String lastName,
            String email,
            String password,
            LocalDateTime createAt,
            LocalDateTime updateUp,
            Set<Role> roles,
            boolean enabled) {
        super.setId(id);
        super.setFirstName(firstName);
        super.setLastName(lastName);
        super.setEmail(email);
        super.setPassword(password);
        super.setCreateAt(createAt);
        super.setUpdateAt(updateUp);
        super.setRoles(roles);
        this.enabled = enabled;
    }

    public Long getId() {
        return super.getId();
    }

    @Override
    public String getUsername() {
        return super.getEmail();
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
        return super.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new HashSet<>();
        Set<Authority> authoritySet = new HashSet<>();
        Set<Role> roles = super.getRoles();
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

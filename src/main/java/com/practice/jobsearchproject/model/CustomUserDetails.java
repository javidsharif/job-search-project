package com.practice.jobsearchproject.model;


import com.practice.jobsearchproject.model.entity.UserAuthentication;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final UserAuthentication userAuthentication;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (userAuthentication.getCompany() == null) {
            return List.of(new SimpleGrantedAuthority(userAuthentication.getUser().getRole().getName()));
        }
        return List.of(new SimpleGrantedAuthority(userAuthentication.getCompany().getRole().getName()));
    }

    @Override
    public String getPassword() {
        if (userAuthentication.getCompany() == null) {
            return userAuthentication.getUser().getUserAuthentication().getPassword();
        }
        return userAuthentication.getCompany().getUserAuthentication().getPassword();
    }

    @Override
    public String getUsername() {
        if (userAuthentication.getCompany() == null) {
            return userAuthentication.getUser().getUserAuthentication().getEmail();
        }
        return userAuthentication.getCompany().getUserAuthentication().getEmail();
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
    public boolean isEnabled() {
        return true;
    }
}

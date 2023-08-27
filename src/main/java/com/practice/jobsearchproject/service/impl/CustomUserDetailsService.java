package com.practice.jobsearchproject.service.impl;

import com.practice.jobsearchproject.model.CustomUserDetails;
import com.practice.jobsearchproject.model.entity.UserAuthentication;
import com.practice.jobsearchproject.repository.UserAuthenticationRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserAuthenticationRepository userAuthRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<UserAuthentication> userAuthOptional = userAuthRepository.findByEmail(email);
        if (userAuthOptional.isPresent()) {
            UserAuthentication userAuth = userAuthOptional.get();
            if (userAuth.getUser() != null) {
                return new CustomUserDetails(userAuth.getUser().getUserAuthentication());
            } else if (userAuth.getCompany() != null) {
                return new CustomUserDetails(userAuth.getCompany().getUserAuthentication());
            }
        }
        throw new UsernameNotFoundException("User or company not found");
    }
}

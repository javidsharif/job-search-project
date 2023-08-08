package com.practice.jobsearchproject.service.impl;

import com.practice.jobsearchproject.model.CustomUserDetails;
import com.practice.jobsearchproject.repository.UserAuthenticationRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserAuthenticationRepository userAuthRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (userAuthRepository.findByEmail(email).get().getUser() != null) {
            return new CustomUserDetails(userAuthRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found")).getUser().getUserAuthentication());
        } else {
            return new CustomUserDetails(userAuthRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found")).getCompany().getUserAuthentication());
        }
    }
}

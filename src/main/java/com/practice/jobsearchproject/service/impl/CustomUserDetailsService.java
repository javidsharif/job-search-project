package com.practice.jobsearchproject.service.impl;

import com.practice.jobsearchproject.model.CustomUserDetails;
import com.practice.jobsearchproject.model.entity.UserAuthentication;
import com.practice.jobsearchproject.repository.UserAuthenticationRepository;
import com.practice.jobsearchproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
//    private final UserRepository userRepository;
    private final UserAuthenticationRepository userAuthRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        UserAuthentication userAuthentication = userAuthRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        return new CustomUserDetails(userAuthentication.getUser());
        return new CustomUserDetails(userAuthRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")).getUser());
    }
}

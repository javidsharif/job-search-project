package com.practice.jobsearchproject.service.impl;

import com.practice.jobsearchproject.config.JwtTokenUtil;
import com.practice.jobsearchproject.exception.NotFoundException;
import com.practice.jobsearchproject.model.CustomUserDetails;
import com.practice.jobsearchproject.model.dto.request.AuthenticationRequest;
import com.practice.jobsearchproject.model.dto.response.AuthenticationResponse;
import com.practice.jobsearchproject.model.dto.response.CompanyAuthenticationResponse;
import com.practice.jobsearchproject.model.dto.response.UserAuthenticationResponse;
import com.practice.jobsearchproject.model.entity.UserAuthentication;
import com.practice.jobsearchproject.model.mapper.CompanyMapper;
import com.practice.jobsearchproject.model.mapper.UserMapper;
import com.practice.jobsearchproject.repository.UserAuthenticationRepository;
import com.practice.jobsearchproject.service.UserAuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserAuthenticationServiceImpl implements UserAuthenticationService {
    private final UserAuthenticationRepository userAuthRepository;
    //    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final CompanyMapper companyMapper;
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService customUserDetailsService;

//    @Override
//    public String setPassword(String verificationToken, ResetPasswordDto resetPasswordDto) {
//        VerificationToken token = verificationTokenRepository.findByToken(verificationToken);
//        if (token == null) {
//            throw new RuntimeException("Invalid token!");
//        }
//        UserAuthentication userAuthentication = token.getUserAuthentication();
//        userAuthentication.setPassword(passwordEncoder.encode(resetPasswordDto.getPassword()));
//        userAuthRepository.save(userAuthentication);
//        verificationTokenRepository.delete(token);
//
//        return "Password updated!";
//    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getEmail(),authenticationRequest.getPassword());
        UserAuthentication userAuthentication = userAuthRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow(() ->
                new NotFoundException(String.format("email with %s not found", authenticationRequest.getEmail())));

        final UserDetails userDetails = customUserDetailsService
                .loadUserByUsername(authenticationRequest.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails);
        if (userAuthentication.getCompany() != null) {
            return CompanyAuthenticationResponse.builder().token(token)
                    .company(companyMapper.convertToCompanyResponseDto(userAuthentication.getCompany())).build();
        } else {
            return UserAuthenticationResponse.builder().token(token)
                    .user(userMapper.toUserResponse(userAuthentication.getUser())).build();
        }
    }
    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}

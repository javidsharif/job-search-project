package com.practice.jobsearchproject.service.impl;

import com.practice.jobsearchproject.config.security.jwt.JwtTokenUtil;
import com.practice.jobsearchproject.config.security.service.CustomUserDetailsService;
import com.practice.jobsearchproject.exception.NotFoundException;
import com.practice.jobsearchproject.exception.PasswordException;
import com.practice.jobsearchproject.model.dto.ResetPasswordDto;
import com.practice.jobsearchproject.model.dto.request.AuthenticationRequest;
import com.practice.jobsearchproject.model.dto.response.AuthenticationResponse;
import com.practice.jobsearchproject.model.dto.response.CompanyAuthenticationResponse;
import com.practice.jobsearchproject.model.dto.response.UserAuthenticationResponse;
import com.practice.jobsearchproject.model.entity.UserAuthentication;
import com.practice.jobsearchproject.model.entity.VerificationToken;
import com.practice.jobsearchproject.model.mapper.CompanyMapper;
import com.practice.jobsearchproject.model.mapper.UserMapper;
import com.practice.jobsearchproject.repository.UserAuthenticationRepository;
import com.practice.jobsearchproject.repository.VerificationTokenRepository;
import com.practice.jobsearchproject.service.UserAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthenticationServiceImpl implements UserAuthenticationService {
    private final UserAuthenticationRepository userAuthRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final CompanyMapper companyMapper;
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final VerificationTokenRepository verificationTokenRepository;

    @Override
    public String setPassword(ResetPasswordDto resetPasswordDto) {
        UserAuthentication userAuthentication = userAuthRepository.findByEmail(resetPasswordDto.getEmail())
                .orElseThrow(() -> new NotFoundException(String.format("email with %s not found", resetPasswordDto.getEmail())));
        String newPassword = resetPasswordDto.getPassword();
        String confirmedPassword = resetPasswordDto.getConfirmPassword();
        if (!newPassword.equals(confirmedPassword)) {
            throw new PasswordException("The password confirmation does not match.");
        }
        if (!newPassword.equals(userAuthentication.getPassword())) {
            userAuthentication.setPassword(passwordEncoder.encode(newPassword));
        }
        userAuthRepository.save(userAuthentication);
        return "Password updated!";
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) throws Exception {
        Authentication authentication = authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        UserAuthentication userAuthentication = userAuthRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow(() ->
                new NotFoundException(String.format("email with %s not found", authenticationRequest.getEmail())));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        final String token = jwtTokenUtil.generateToken(userDetails);
        if (verificationTokenRepository.findByUserAuthentication(userAuthentication).isPresent()) {
            VerificationToken verificationToken = verificationTokenRepository.findByUserAuthentication(userAuthentication).get();
            verificationTokenRepository.delete(verificationToken);
        }
        VerificationToken verificationToken = new VerificationToken(userAuthentication);
        verificationTokenRepository.save(verificationToken);

        if (userAuthentication.getCompany() != null) {
            return CompanyAuthenticationResponse.builder().token(token)
                    .company(companyMapper.convertToCompanyResponseDto(userAuthentication.getCompany())).build();
        } else {
            return UserAuthenticationResponse.builder().token(token)
                    .user(userMapper.toUserResponse(userAuthentication.getUser())).build();
        }
    }

    private Authentication authenticate(String email, String password) throws Exception {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}

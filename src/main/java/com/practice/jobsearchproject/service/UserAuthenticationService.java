package com.practice.jobsearchproject.service;

import com.practice.jobsearchproject.model.dto.request.AuthenticationRequest;
import com.practice.jobsearchproject.model.dto.response.AuthenticationResponse;

public interface UserAuthenticationService {
//    String setPassword(String token, ResetPasswordDto resetPasswordDto);

    AuthenticationResponse login(AuthenticationRequest authenticationRequest) throws Exception;
}

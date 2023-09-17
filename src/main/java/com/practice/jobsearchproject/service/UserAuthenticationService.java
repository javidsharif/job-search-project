package com.practice.jobsearchproject.service;

import com.practice.jobsearchproject.model.dto.ResetPasswordDto;
import com.practice.jobsearchproject.model.dto.request.AuthenticationRequest;
import com.practice.jobsearchproject.model.dto.response.AuthenticationResponse;

public interface UserAuthenticationService {
    String setPassword(ResetPasswordDto resetPasswordDto);

    AuthenticationResponse login(AuthenticationRequest authenticationRequest) throws Exception;
}

package com.practice.jobsearchproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordDto {
    @NotNull
    @NotBlank(message = "email is mandatory")
    private String email;
    @NotNull
    @NotBlank(message = "password is mandatory")
    private String password;
    @NotNull
    @NotBlank(message = "confirm password is mandatory")
    private String confirmPassword;
}

package com.practice.jobsearchproject.model.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class CompanyRequestDto {
    @NotBlank(message = "Name is mandatory")
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "Numbers and the first letter starting with a lowercase letter not allowed")
    private String name;
    @NotNull
    @NotBlank(message = "Password is mandatory")
    private String password;
    @NotNull
    @NotBlank(message = "Confirm password is mandatory")
    private String confirmPassword;
    @NotNull
    @NotBlank(message = "Email is mandatory")
    @Email(regexp = "^(.+)@(.+)$", message = "Email is wrong")
    private String email;
//    @Pattern(regexp = "\\d{3}\\s\\d{2}\\s\\d{3}\\s\\d{2}\\s\\d{2}", message = "Telephone is wrong")
    private String telephone;
    @NotNull
    @NotBlank(message = "Cv email is mandatory")
    @Email(regexp = "^(.+)@(.+)$", message = "Cv email is wrong")
    private String cvEmail;
    private String information;
    private String photoUrl;
}

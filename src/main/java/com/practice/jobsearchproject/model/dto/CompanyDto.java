package com.practice.jobsearchproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {
    @NotBlank(message = "name is mandatory")
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "Numbers not allowed")
    private String name;
    @NotNull
    @NotBlank(message = "password is mandatory")
    private String password;
    @NotNull
    @NotBlank(message = "confirm password is mandatory")
    private String confirmPassword;
    @NotNull
    @NotBlank(message = "email is mandatory")
    @Email(regexp = "^(.+)@(.+)$", message = "Email is wrong")
    private String email;
//    @Pattern(regexp = "\\d{3}\\s\\d{2}\\s\\d{3}\\s\\d{2}\\s\\d{2}", message = "Telephone is wrong")
    private String telephone;
    @NotNull
    @NotBlank(message = "cv email is mandatory")
    @Email(regexp = "^(.+)@(.+)$", message = "Email is wrong")
    private String cvEmail;
    private String information;
    private String photoUrl;
    private String city;
    private String fieldOfActivity;
    private LocalDate foundationDate;
    private Integer numberOfEmployees;
    private String address;
    private String siteOfCompany;
    private String facebookProfileLink;
    private String twitterProfileLink;
    private String linkedinProfileLink;
    private String instagramProfileLink;
}

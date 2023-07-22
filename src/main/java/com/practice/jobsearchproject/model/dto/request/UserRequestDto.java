package com.practice.jobsearchproject.model.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class UserRequestDto {
    @NotBlank(message = "name is mandatory")
    @Pattern(regexp = "\\D+", message = "Numbers not allowed")
    private String name;
    @NotBlank(message = "surname is mandatory")
    @Pattern(regexp = "\\D+", message = "Numbers not allowed")
    private String surname;
    @NotNull
    private String password;
    @NotNull
    private String confirmPassword;
    @NotNull
    @NotBlank(message = "email is mandatory")
    @Email(message = "Email is wrong")
    private String email;
    private Date dateOfBirth;
    private String phone;
    private String gender;
    private String city;
    private String photoUrl;

}

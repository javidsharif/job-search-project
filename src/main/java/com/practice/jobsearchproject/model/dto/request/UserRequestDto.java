package com.practice.jobsearchproject.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "Numbers and the first letter starting with a lowercase letter not allowed")
    @NotBlank(message = "name is mandatory")
    private String name;
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "Numbers and the first letter starting with a lowercase letter not allowed")
    @NotBlank(message = "surname is mandatory")
    private String surname;
    @NotBlank(message = "password is mandatory")
    private String password;
    @NotBlank(message = "confirm password is mandatory")
    private String confirmPassword;
    @NotNull
    @NotBlank(message = "email is mandatory")
    @Email(message = "Email is wrong")
    private String email;
    private Date dateOfBirth;
    private String phone;
    private String gender;
    private String city;
}

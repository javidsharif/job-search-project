//package com.practice.jobsearchproject.model.dto;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Pattern;
//import java.util.Date;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class UserDto {
//    @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "Numbers and the first letter starting with a lowercase letter not allowed")
//    @NotBlank(message = "name is mandatory")
//    private String name;
//    @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "Numbers and the first letter starting with a lowercase letter not allowed")
//    @NotBlank(message = "surname is mandatory")
//    private String surname;
//    @NotNull
//    @NotBlank(message = "password is mandatory")
//    private String password;
//    @NotNull
//    @NotBlank(message = "confirm password is mandatory")
//    private String confirmPassword;
//    @NotNull
//    @NotBlank(message = "email is mandatory")
//    @Email(message = "Email is wrong")
//    private String email;
//    private Date dateOfBirth;
//    @Pattern(regexp = "\\d{3}\\s\\d{2}\\s\\d{3}\\s\\d{2}\\s\\d{2}", message = "Telephone is wrong")
//    private String phone;
//    private String gender;
//    private String city;
//}

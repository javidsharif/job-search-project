package com.practice.jobsearchproject.model.dto.response;

import com.practice.jobsearchproject.model.dto.RoleDto;
import lombok.Data;

import java.util.Date;

@Data
public class UserResponse {
    private String name;
    private String surname;
    private String phone;
    private String gender;
    private String city;
    private Date dateOfBirth;

    private RoleDto role;
    private String photoUrl;
}

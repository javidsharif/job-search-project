package com.practice.jobsearchproject.model.dto.response;

import com.practice.jobsearchproject.model.dto.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponseDto {
    private String name;
    private String telephone;
    private String cvEmail;
    private String information;
    private LocalDateTime createdAt;
    private String photoUrl;
    private String city;
    private RoleDto role;
    private String fieldOfActivity;
    private Integer numberOfEmployees;
    private String address;
    private String siteOfCompany;
    private String facebookProfileLink;
    private String twitterProfileLink;
    private String linkedinProfileLink;
    private String instagramProfileLink;
}

package com.practice.jobsearchproject.model.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class EducationResponse {
    private String educationName;
    private Integer level;
    private String faculty;
    private Date dateOfGraduation;
    private String additionalInformation;
}

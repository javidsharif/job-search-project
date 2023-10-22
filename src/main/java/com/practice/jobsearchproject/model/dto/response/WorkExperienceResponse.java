package com.practice.jobsearchproject.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class WorkExperienceResponse {
    private String company;
    private String position;
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date dateOfStartJob;
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date dateOfEndJob;
    private boolean isContinuedJob;
    private String additionalInformation;
}

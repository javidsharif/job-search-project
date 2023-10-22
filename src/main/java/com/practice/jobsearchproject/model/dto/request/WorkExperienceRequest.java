package com.practice.jobsearchproject.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Getter
@Setter
@ToString
public class WorkExperienceRequest {
    private String company;
    @Length(max = 20)
    private String position;
    private Date dateOfStartJob;
    private Date dateOfEndJob;
    private boolean isContinuedJob;
    @Length(max = 1000, message = "Text doesn't must contains more 1000 length")
    private String additionalInformation;
}

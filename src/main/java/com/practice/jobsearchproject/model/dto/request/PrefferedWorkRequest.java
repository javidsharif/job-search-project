package com.practice.jobsearchproject.model.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
public class PrefferedWorkRequest {
    private String areaInWhichYouWork;
    private String jobGraphic;
    private String workLevel;
    private String studyLevel;
    @Pattern(regexp = "^[0-9]+$\n", message = "it must be contains only numbers")
    private String prefferedSalary;
    private String currency;
    private String specialKnowledge;
    @Length(max = 4)
    private String workExperience;
}

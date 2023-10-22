package com.practice.jobsearchproject.model.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PrefferedWorkDto {
    private String areaInWhichYouWork;
    private String jobGraphic;
    private String workLevel;
    private String studyLevel;
    private String prefferedSalary;
    private String currency;
    private String specialKnowledge;
    private String workExperience;
}

package com.practice.jobsearchproject.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublicResumeResponse {
    private String nameAndSurname;
    private String position;
    private String areaInWhichYouWork;
    private String prefferedSalary;
    private String photoUrl;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime updateDate;
}

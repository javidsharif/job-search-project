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
public class EducationRequest {
    private String educationName;
    private Integer level;
    @Length(max = 20, message = "faculty doesn't must contain more 20 length")
    private String faculty;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfGraduation;
    @Length(max = 1000, message = "additional information doesn't must contain more 20 length")
    private String additionalInformation;
}

package com.practice.jobsearchproject.model.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;

@Getter
@Setter
@ToString
public class KnowledgeRequest {
    @Length(max = 20, message = "name doesn't must contain more 20 length")
    private String name;
    @Max(value = 100, message = "max value 100")
    private Integer level;
}

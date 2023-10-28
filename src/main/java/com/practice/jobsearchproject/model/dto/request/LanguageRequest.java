package com.practice.jobsearchproject.model.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString
public class LanguageRequest {
    @Length(max = 20, message = "name doesn't must contain more 20 length")
    private String name;
    private String level;
}

package com.practice.jobsearchproject.model.dto.request;

import com.practice.jobsearchproject.utills.Updatable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString
public class AchievementAndAwardRequest {
    @Length(max = 30, message = "name doesn't must contain more 30 length")
    private String name;
    @Length(max = 4, message = "year doesn't must contain more 4 length")
    private String year;
    @Length(max = 1000, message = "description doesn't must contain more 1000 length")
    private String description;
}

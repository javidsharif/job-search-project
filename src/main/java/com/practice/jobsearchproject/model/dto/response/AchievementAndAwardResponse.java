package com.practice.jobsearchproject.model.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AchievementAndAwardResponse {
    private String name;
    private String year;
    private String description;
}

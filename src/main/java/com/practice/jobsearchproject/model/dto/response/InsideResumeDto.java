package com.practice.jobsearchproject.model.dto.response;

import com.practice.jobsearchproject.model.entity.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InsideResumeDto {
    private String nameAndSurname;
    private String phone;
    private String email;
    private String preferredMeansOfCommunication;
    private String position;
    private String aboutUs;
    private String facebookLink;
    private String linkedinLink;
    private String githubLink;
    private String behanceLink;
    private Boolean isPostCv;

    private PrefferedWorkDto prefferedWork;
    private List<WorkExperienceResponse> workExperiences;
    private List<EducationResponse> educations;
    private List<KnowledgeResponse> knowledgeList;
    private List<AchievementAndAwardResponse> achievementAndAwards;
    private List<LanguageResponse> languages;
}

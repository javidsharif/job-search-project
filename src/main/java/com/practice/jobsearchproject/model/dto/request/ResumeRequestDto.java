package com.practice.jobsearchproject.model.dto.request;

import com.practice.jobsearchproject.model.dto.response.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
@ToString
public class ResumeRequestDto {
    @Pattern(regexp = "^[A-Za-zА-Яа-я]*$|^$", message = "Name And Surname must be only letters or empty")
    private String nameAndSurname;
    @Pattern(regexp = "^(\\(\\d{3}\\) \\d{3}-\\d{4})?|$", message = "Incorrect phone number format")
    private String phone;
    @NotNull
    @NotBlank(message = "email is mandatory")
    @Email(message = "Email is wrong")
    private String email;
    private String preferredMeansOfCommunication;
    private String position;
    private String aboutUs;
    private String facebookLink;
    private String linkedinLink;
    private String githubLink;
    private String behanceLink;
    private Boolean isPostCv;

    private PrefferedWorkRequest prefferedWork;
    private List<WorkExperienceRequest> workExperiences;
    private List<EducationRequest> educations;
    private List<KnowledgeRequest> knowledgeList;
    private List<AchievementAndAwardRequest> achievementAndAwards;
    private List<LanguageRequest> languages;
}

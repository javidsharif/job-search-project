package com.practice.jobsearchproject.model.mapper;

import com.practice.jobsearchproject.model.dto.request.*;
import com.practice.jobsearchproject.model.dto.response.*;
import com.practice.jobsearchproject.model.entity.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ResumeMapper {
    List<ResumeResponseDto> toResumeResponse(List<Resume> resume);

    InsideResumeDto toInsideResume(Resume resume);

    PrefferedWorkDto toPrefferedWork(PrefferedWork prefferedWork);

    List<EducationResponse> toEducationResponseList(List<Education> educationList);

    List<AchievementAndAwardResponse> toAchievementAndAwardResponseList(List<AchievementAndAward> achievementAndAwards);

    List<WorkExperienceResponse> toWorkExperienceResponseList(List<WorkExperience> workExperiences);

    List<LanguageResponse> toLanguageResponseList(List<Language> languages);

    List<KnowledgeResponse> toKnowledgeResponseList(List<Knowledge> knowledgeList);

    PrefferedWork toPrefferedWork(PrefferedWorkRequest prefferedWorkRequest);

    Resume toResume(ResumeRequestDto resumeRequestDto);

    List<Education> toEducationList(List<EducationRequest> educationRequestList);

    List<Language> toLanguageList(List<LanguageRequest> languageRequestList);
    List<Knowledge> toKnowledgeList(List<KnowledgeRequest> knowledgeRequestList);
    List<AchievementAndAward> toAchievementAndAwardList(List<AchievementAndAwardRequest> achievementAndAwards);
    List<WorkExperience> toWorkExperienceList(List<WorkExperienceRequest> workExperienceList);


}

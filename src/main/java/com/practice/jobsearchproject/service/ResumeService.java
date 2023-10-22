package com.practice.jobsearchproject.service;

import com.practice.jobsearchproject.config.security.service.CustomUserDetails;
import com.practice.jobsearchproject.model.dto.request.ResumeRequestDto;
import com.practice.jobsearchproject.model.dto.response.InsideResumeDto;
import com.practice.jobsearchproject.model.dto.response.PublicResumeResponse;
import com.practice.jobsearchproject.model.dto.response.ResumeResponseDto;
import com.practice.jobsearchproject.model.entity.User;

import java.util.List;

public interface ResumeService {
    List<ResumeResponseDto> getCandidateResumes(Long id);

    InsideResumeDto findResumeById(Long id);

    void createResume(User user, ResumeRequestDto resumeRequestDto);

    void updateResume(Long id, ResumeRequestDto resumeRequestDto);

    void removeResumeById(Long id);

    List<PublicResumeResponse> getAllResumes();

    InsideResumeDto findPublicResumeById(Long id);
}

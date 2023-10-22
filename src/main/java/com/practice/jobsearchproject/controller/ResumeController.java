package com.practice.jobsearchproject.controller;

import com.practice.jobsearchproject.config.security.service.CustomUserDetails;
import com.practice.jobsearchproject.model.dto.request.ResumeRequestDto;
import com.practice.jobsearchproject.model.dto.response.InsideResumeDto;
import com.practice.jobsearchproject.model.dto.response.PublicResumeResponse;
import com.practice.jobsearchproject.model.dto.response.ResumeResponseDto;
import com.practice.jobsearchproject.model.entity.Resume;
import com.practice.jobsearchproject.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ResumeController {

    private final ResumeService resumeService;

    @GetMapping("/candidate/resume")
    public List<ResumeResponseDto> getCandidateResumes(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return resumeService.getCandidateResumes(userDetails.getUserAuthentication().getUser().getId());
    }
    @GetMapping("/candidate/resume/{id}")
    public InsideResumeDto findById(@PathVariable Long id) {
        return resumeService.findResumeById(id);
    }

    @PostMapping("/candidate/resume")
    @ResponseStatus(HttpStatus.CREATED)
    public void createResume(@AuthenticationPrincipal CustomUserDetails userDetails,@Valid @RequestBody ResumeRequestDto resumeRequestDto) {
     resumeService.createResume(userDetails.getUserAuthentication().getUser(), resumeRequestDto);
    }

    @PutMapping("/candidate/resume/{id}")
    public void updateResume(@PathVariable Long id, @Valid @RequestBody ResumeRequestDto resumeRequestDto){
        resumeService.updateResume(id, resumeRequestDto);
    }

    @DeleteMapping("/candidate/resume/{id}")
    public void deleteResume(@PathVariable Long id) {
        resumeService.removeResumeById(id);
    }

    @GetMapping("/resumes")
    public List<PublicResumeResponse> getAllResumes() {
        return resumeService.getAllResumes();
    }

    @GetMapping("/resume/{id}")
    public InsideResumeDto findPublicResumeById(@PathVariable Long id) {
        return resumeService.findPublicResumeById(id);
    }
}

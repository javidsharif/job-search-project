package com.practice.jobsearchproject.controller;

import com.practice.jobsearchproject.model.dto.response.JobTypeResponseDto;
import com.practice.jobsearchproject.service.JobTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/jobTypes")
public class JobTypeController {
    private final JobTypeService jobTypeService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<JobTypeResponseDto> getAllJobTypes() {
        return jobTypeService.getAllJobTypes();
    }
}

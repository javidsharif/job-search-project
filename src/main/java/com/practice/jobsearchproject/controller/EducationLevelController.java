package com.practice.jobsearchproject.controller;

import com.practice.jobsearchproject.model.dto.response.EducationLevelResponseDto;
import com.practice.jobsearchproject.service.EducationLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/educationLevel")
public class EducationLevelController {
    private final EducationLevelService educationLevelService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EducationLevelResponseDto> getAllEducationLevels() {
        return educationLevelService.getAllEducationLevels();
    }
}
package com.practice.jobsearchproject.service;

import com.practice.jobsearchproject.model.dto.response.EducationLevelResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EducationLevelService {
    List<EducationLevelResponseDto> getAllEducationLevels();
}

package com.practice.jobsearchproject.service;

import com.practice.jobsearchproject.model.dto.response.JobTypeResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JobTypeService {
    List<JobTypeResponseDto> getAllJobTypes();
}

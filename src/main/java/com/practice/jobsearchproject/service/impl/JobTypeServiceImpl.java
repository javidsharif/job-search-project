package com.practice.jobsearchproject.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.jobsearchproject.model.dto.response.JobTypeResponseDto;
import com.practice.jobsearchproject.model.entity.JobType;
import com.practice.jobsearchproject.model.mapper.JobTypeMapper;
import com.practice.jobsearchproject.repository.JobTypeRepository;
import com.practice.jobsearchproject.service.JobTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class JobTypeServiceImpl implements JobTypeService {
    private final JobTypeRepository jobTypeRepository;
    private final JobTypeMapper jobTypeMapper;

    public void saveAllJobTypesFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File("job-types.json");
            String[] jobTypes = objectMapper.readValue(file, String[].class);
            for (String name : jobTypes) {
                JobType jobType = new JobType();
                jobType.setName(name);
                jobTypeRepository.save(jobType);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<JobTypeResponseDto> getAllJobTypes() {
        return jobTypeRepository.findAll().stream()
                .map(jobTypeMapper::convertToJobTypeResponseDto)
                .collect(Collectors.toList());
    }
}
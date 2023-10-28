package com.practice.jobsearchproject.model.mapper;

import com.practice.jobsearchproject.model.dto.response.JobTypeResponseDto;
import com.practice.jobsearchproject.model.entity.JobType;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface JobTypeMapper {
    JobTypeResponseDto convertToJobTypeResponseDto(JobType jobType);
    Set<JobType> convertToJobTypes(Set<JobTypeResponseDto> jobTypeResponseDtos);
    Set<JobTypeResponseDto> convertToJobTypeResponseDtos(Set<JobType> jobTypes);
}

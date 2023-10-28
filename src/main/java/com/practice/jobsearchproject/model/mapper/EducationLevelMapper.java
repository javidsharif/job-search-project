package com.practice.jobsearchproject.model.mapper;

import com.practice.jobsearchproject.model.dto.response.EducationLevelResponseDto;
import com.practice.jobsearchproject.model.entity.EducationLevel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EducationLevelMapper {
    EducationLevelResponseDto convertToEducationLevelResponseDto(EducationLevel educationLevel);

    EducationLevel convertToEducationLevel(EducationLevelResponseDto educationLevelResponseDto);
}

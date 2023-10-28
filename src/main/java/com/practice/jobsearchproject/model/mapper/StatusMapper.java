package com.practice.jobsearchproject.model.mapper;

import com.practice.jobsearchproject.model.dto.response.StatusResponseDto;
import com.practice.jobsearchproject.model.entity.Status;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatusMapper {
    StatusResponseDto convertToStatusResponseDto(Status status);
}

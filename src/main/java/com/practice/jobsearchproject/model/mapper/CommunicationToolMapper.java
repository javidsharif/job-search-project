package com.practice.jobsearchproject.model.mapper;

import com.practice.jobsearchproject.model.dto.response.CommunicationToolResponseDto;
import com.practice.jobsearchproject.model.entity.CommunicationTool;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommunicationToolMapper {
    CommunicationToolResponseDto convertToCommunicationToolResponseDto(CommunicationTool communicationTool);
    CommunicationTool convertToCommunicationTool(CommunicationToolResponseDto communicationToolResponseDto);
}

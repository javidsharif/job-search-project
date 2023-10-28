package com.practice.jobsearchproject.model.mapper;

import com.practice.jobsearchproject.model.dto.response.SpecialKnowledgeResponseDto;
import com.practice.jobsearchproject.model.entity.SpecialKnowledge;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface SpecialKnowledgeMapper {
    SpecialKnowledgeResponseDto convertToSpecialKnowledgeResponseDto(SpecialKnowledge specialKnowledge);

    Set<SpecialKnowledge> convertToSpecialKnowledge(Set<SpecialKnowledgeResponseDto> specialKnowledgeResponseDto);

    Set<SpecialKnowledgeResponseDto> convertToSpecialKnowledgeResponseDtos(Set<SpecialKnowledge> specialKnowledge);
}

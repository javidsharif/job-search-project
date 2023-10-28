package com.practice.jobsearchproject.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecialKnowledgeResponseDto {
    private Integer id;
    private String name;
}

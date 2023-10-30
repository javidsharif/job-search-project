package com.practice.jobsearchproject.service;

import com.practice.jobsearchproject.model.dto.response.SpecialKnowledgeResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SpecialKnowledgeService {
    List<SpecialKnowledgeResponseDto> getAllSpecialKnowledge();
}

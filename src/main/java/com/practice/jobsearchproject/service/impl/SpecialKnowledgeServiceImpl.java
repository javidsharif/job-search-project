package com.practice.jobsearchproject.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.jobsearchproject.model.dto.response.SpecialKnowledgeResponseDto;
import com.practice.jobsearchproject.model.entity.SpecialKnowledge;
import com.practice.jobsearchproject.model.mapper.SpecialKnowledgeMapper;
import com.practice.jobsearchproject.repository.SpecialKnowledgeRepository;
import com.practice.jobsearchproject.service.SpecialKnowledgeService;
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
public class SpecialKnowledgeServiceImpl implements SpecialKnowledgeService {
    private final SpecialKnowledgeRepository specialKnowledgeRepository;
    private final SpecialKnowledgeMapper specialKnowledgeMapper;

    public void saveAllSpecialKnowledgeFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File("special-knowledge.json");
            String[] specialKnowledge = objectMapper.readValue(file, String[].class);
            for (String name : specialKnowledge) {
                SpecialKnowledge knowledge = new SpecialKnowledge();
                knowledge.setName(name);
                specialKnowledgeRepository.save(knowledge);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SpecialKnowledgeResponseDto> getAllSpecialKnowledge() {
        return specialKnowledgeRepository.findAll().stream()
                .map(specialKnowledgeMapper::convertToSpecialKnowledgeResponseDto)
                .collect(Collectors.toList());
    }
}
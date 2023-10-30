package com.practice.jobsearchproject.controller;

import com.practice.jobsearchproject.model.dto.response.SpecialKnowledgeResponseDto;
import com.practice.jobsearchproject.service.SpecialKnowledgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/specialKnowledge")
public class SpecialKnowledgeController {
    private final SpecialKnowledgeService specialKnowledgeService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SpecialKnowledgeResponseDto> getAllSpecialKnowledge() {
        return specialKnowledgeService.getAllSpecialKnowledge();
    }
}

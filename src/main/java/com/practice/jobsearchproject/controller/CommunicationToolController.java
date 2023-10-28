package com.practice.jobsearchproject.controller;

import com.practice.jobsearchproject.model.dto.response.CommunicationToolResponseDto;
import com.practice.jobsearchproject.service.CommunicationToolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/communicationTools")
public class CommunicationToolController {
    private final CommunicationToolService communicationToolService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommunicationToolResponseDto> getAllCommunicationTools() {
        return communicationToolService.getAllCommunicationTools();
    }
}

package com.practice.jobsearchproject.service.impl;

import com.practice.jobsearchproject.model.dto.response.CommunicationToolResponseDto;
import com.practice.jobsearchproject.model.mapper.CommunicationToolMapper;
import com.practice.jobsearchproject.repository.CommunicationToolRepository;
import com.practice.jobsearchproject.service.CommunicationToolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommunicationToolServiceImpl implements CommunicationToolService {
    private final CommunicationToolRepository communicationToolRepository;
    private final CommunicationToolMapper communicationToolMapper;

    @Override
    public List<CommunicationToolResponseDto> getAllCommunicationTools() {
        return communicationToolRepository.findAll().stream()
                .map(communicationToolMapper::convertToCommunicationToolResponseDto)
                .collect(Collectors.toList());
    }
}

package com.practice.jobsearchproject.service.impl;

import com.practice.jobsearchproject.model.dto.response.StatusResponseDto;
import com.practice.jobsearchproject.model.mapper.StatusMapper;
import com.practice.jobsearchproject.repository.StatusRepository;
import com.practice.jobsearchproject.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StatusServiceImpl implements StatusService {
    private final StatusRepository statusRepository;
    private final StatusMapper statusMapper;

    @Override
    public List<StatusResponseDto> getAllStatuses() {
        return statusRepository.findAll().stream()
                .map(statusMapper::convertToStatusResponseDto)
                .collect(Collectors.toList());
    }
}

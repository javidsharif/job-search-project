package com.practice.jobsearchproject.service;

import com.practice.jobsearchproject.model.dto.response.StatusResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StatusService {
    List<StatusResponseDto> getAllStatuses();
}

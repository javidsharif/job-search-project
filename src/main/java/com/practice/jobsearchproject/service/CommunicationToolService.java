package com.practice.jobsearchproject.service;

import com.practice.jobsearchproject.model.dto.response.CommunicationToolResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommunicationToolService {
    List<CommunicationToolResponseDto> getAllCommunicationTools();
}

package com.practice.jobsearchproject.service;

import com.practice.jobsearchproject.model.dto.response.CityResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CityService {
    List<CityResponseDto> getAllCities();
}

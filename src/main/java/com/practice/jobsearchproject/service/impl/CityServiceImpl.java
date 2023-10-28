package com.practice.jobsearchproject.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.jobsearchproject.model.dto.response.CityResponseDto;
import com.practice.jobsearchproject.model.entity.City;
import com.practice.jobsearchproject.model.mapper.CityMapper;
import com.practice.jobsearchproject.repository.CityRepository;
import com.practice.jobsearchproject.service.CityService;
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
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    public void saveAllCitiesFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File("cities.json");
            String[] cities = objectMapper.readValue(file, String[].class);
            for (String name : cities) {
                City city = new City();
                city.setName(name);
                cityRepository.save(city);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<CityResponseDto> getAllCities() {
        return cityRepository.findAll().stream()
                .map(cityMapper::convertToCityResponseDto)
                .collect(Collectors.toList());
    }
}
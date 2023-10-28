package com.practice.jobsearchproject.controller;

import com.practice.jobsearchproject.model.dto.response.CityResponseDto;
import com.practice.jobsearchproject.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cities")
public class CityController {
    private final CityService cityService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CityResponseDto> getAllCities() {
        return cityService.getAllCities();
    }
}

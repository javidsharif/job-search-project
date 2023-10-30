package com.practice.jobsearchproject.model.mapper;

import com.practice.jobsearchproject.model.dto.response.CityResponseDto;
import com.practice.jobsearchproject.model.entity.City;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface CityMapper {
    CityResponseDto convertToCityResponseDto(City city);

    Set<City> convertToCities(Set<CityResponseDto> cityResponseDtos);
    Set<CityResponseDto> convertToCityResponseDtos(Set<City> cities);
}

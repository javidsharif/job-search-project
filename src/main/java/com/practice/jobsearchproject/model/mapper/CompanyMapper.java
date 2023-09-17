package com.practice.jobsearchproject.model.mapper;

import com.practice.jobsearchproject.model.dto.CompanyDto;
import com.practice.jobsearchproject.model.dto.response.CompanyResponseDto;
import com.practice.jobsearchproject.model.entity.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CompanyResponseDto convertToCompanyResponseDto(Company company);

    CompanyDto convertToCompanyDto(Company company);
}

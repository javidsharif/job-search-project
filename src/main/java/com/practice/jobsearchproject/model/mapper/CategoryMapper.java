package com.practice.jobsearchproject.model.mapper;

import com.practice.jobsearchproject.model.dto.response.CategoryResponseDto;
import com.practice.jobsearchproject.model.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponseDto convertToCategoryResponseDto(Category category);
    Category convertToCategory(CategoryResponseDto categoryResponseDto);
}

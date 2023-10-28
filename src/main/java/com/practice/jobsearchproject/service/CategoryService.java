package com.practice.jobsearchproject.service;

import com.practice.jobsearchproject.model.dto.response.CategoryResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<CategoryResponseDto> getAllCategories();
}

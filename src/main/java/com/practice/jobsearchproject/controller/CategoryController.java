package com.practice.jobsearchproject.controller;

import com.practice.jobsearchproject.model.dto.response.CategoryResponseDto;
import com.practice.jobsearchproject.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponseDto> getAllCategories() {
        return categoryService.getAllCategories();
    }
}

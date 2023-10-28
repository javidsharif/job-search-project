package com.practice.jobsearchproject.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.jobsearchproject.model.dto.response.CategoryResponseDto;
import com.practice.jobsearchproject.model.entity.Category;
import com.practice.jobsearchproject.model.mapper.CategoryMapper;
import com.practice.jobsearchproject.repository.CategoryRepository;
import com.practice.jobsearchproject.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public void saveAllCategoriesFromJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(new File("job-titles-categories.json"));

            List<Category> allCategories = new ArrayList<>();
            processJsonNode(jsonNode, allCategories);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processJsonNode(JsonNode jsonNode, List<Category> allCategories) {
        for (JsonNode node : jsonNode) {
            Category category = new Category();
            category.setName(node.get("name").asText());
            category.setParentId(node.get("parentId").asLong());
            categoryRepository.save(category);
            JsonNode children = node.get("children");
            if (children != null && children.isArray()) {
                Category lastCategory = categoryRepository.findAll().get(categoryRepository.findAll().size()-1);
                for (JsonNode node1 : children) {
                    Category category1 = new Category();
                    category1.setName(node1.get("name").asText());
                    category1.setParentId(lastCategory.getId());
                    categoryRepository.save(category1);
                }
            }
        }
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::convertToCategoryResponseDto)
                .collect(Collectors.toList());
    }
}
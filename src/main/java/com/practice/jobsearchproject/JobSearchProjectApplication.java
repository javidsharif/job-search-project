package com.practice.jobsearchproject;

import com.practice.jobsearchproject.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class JobSearchProjectApplication {
//    @Autowired
//    private CategoryServiceImpl categoryService;
//    @Autowired
//    private CityServiceImpl cityService;
//    @Autowired
//    private CurrencyServiceImpl currencyService;
//    @Autowired
//    private JobTypeServiceImpl jobTypeService;
//    @Autowired
//    private SpecialKnowledgeServiceImpl specialKnowledgeService;
//    @Autowired
//    private EducationLevelForVacancyServiceImpl educationLevelService;
//
//    @PostConstruct
//    public void init() {
//        categoryService.saveAllCategoriesFromJson();
//        cityService.saveAllCitiesFromJson();
//        currencyService.saveAllCurrenciesFromJson();
//        jobTypeService.saveAllJobTypesFromJson();
//        specialKnowledgeService.saveAllSpecialKnowledgeFromJson();
//        educationLevelService.saveAllEducationLevelsFromJson();
//    }

    public static void main(String[] args) {
        SpringApplication.run(JobSearchProjectApplication.class, args);
    }
}
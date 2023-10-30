package com.practice.jobsearchproject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.jobsearchproject.model.entity.*;
import com.practice.jobsearchproject.repository.*;
import com.practice.jobsearchproject.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class JobSearchProjectApplication {
    public static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private CategoryServiceImpl categoryService;
    @Autowired
    private CityServiceImpl cityService;
    @Autowired
    private CurrencyServiceImpl currencyService;
    @Autowired
    private JobTypeServiceImpl jobTypeService;
    @Autowired
    private SpecialKnowledgeServiceImpl specialKnowledgeService;
    @Autowired
    private EducationLevelForVacancyServiceImpl educationLevelService;

    @PostConstruct
    public void init() {
        categoryService.saveAllCategoriesFromJson();
        cityService.saveAllCitiesFromJson();
        currencyService.saveAllCurrenciesFromJson();
        jobTypeService.saveAllJobTypesFromJson();
        specialKnowledgeService.saveAllSpecialKnowledgeFromJson();
        educationLevelService.saveAllEducationLevelsFromJson();
    }

    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext = SpringApplication.run(JobSearchProjectApplication.class, args);
        ListCompanyRepository ls = applicationContext.getBean(ListCompanyRepository.class);
        OccupationRepository ocp  = applicationContext.getBean(OccupationRepository.class);
        EducationLevelRepository elr  = applicationContext.getBean(EducationLevelRepository.class);
        JobGraphicRepository jgr  = applicationContext.getBean(JobGraphicRepository.class);
        UniversityRepository ur  = applicationContext.getBean(UniversityRepository.class);
        saveOccupationToDatabase(ocp);
        saveToDatabase(ls);
        saveEducationLevelToDB(elr);
        saveJobGraphicToDB(jgr);
        saveUniversitiesToDB(ur);
    }

    public static void saveToDatabase(ListCompanyRepository listCompanyRepository) throws IOException {
        File fileReader = new File("companies.json");
        List<String> listCompanies=  objectMapper.readValue(fileReader, new TypeReference<>() {});
        for (String companyName : listCompanies) {
            ListCompany listCompany = new ListCompany();
            listCompany.setName(companyName);
            listCompanyRepository.save(listCompany);
        }
    }
    public static void saveOccupationToDatabase(OccupationRepository occupationRepository) throws IOException {
        File fileReader = new File("occupations.json");
        List<String> listOccupations=  objectMapper.readValue(fileReader, new TypeReference<>() {});
        for (String occupationName : listOccupations) {
            Occupation occupation = new Occupation();
            occupation.setOccupation(occupationName);
            occupationRepository.save(occupation);
        }
    }

    public static void saveEducationLevelToDB(EducationLevelRepository educationLevelRepository) throws IOException {
        File fileReader = new File("education-level.json");
        List<String> listEducationLevel=  objectMapper.readValue(fileReader, new TypeReference<>() {});
        for (String levelName : listEducationLevel) {
            EducationLevel educationLevel = new EducationLevel();
            educationLevel.setName(levelName);
            educationLevelRepository.save(educationLevel);
        }
    }
    public static void saveJobGraphicToDB(JobGraphicRepository jobGraphicRepository) throws IOException {
        File fileReader = new File("job-graphic.json");

        List<String> listJobGraphic=  objectMapper.readValue(fileReader, new TypeReference<>() {});
        for (String jobGraph : listJobGraphic) {
            JobGraphic jobGraphic = new JobGraphic();
            jobGraphic.setName(jobGraph);
            jobGraphicRepository.save(jobGraphic);
        }
    }

    public static void saveUniversitiesToDB(UniversityRepository universityRepository) throws IOException {
        File fileReader = new File("universities.json");

        List<String> listUniversity=  objectMapper.readValue(fileReader, new TypeReference<>() {});
        for (String university : listUniversity) {
            University uni = new University();
            uni.setName(university);
            universityRepository.save(uni);
        }
    }
}
package com.practice.jobsearchproject.controller;

import com.practice.jobsearchproject.model.entity.*;
import com.practice.jobsearchproject.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ListController {
    private ListCompanyRepository listCompanyRepository;
    private JobGraphicRepository jobGraphicRepository;
    private EducationLevelRepository educationLevelRepository;
    private OccupationRepository occupationRepository;
    private UniversityRepository universityRepository;

    @GetMapping("/get-companies")
    public List<ListCompany> getAllCompanies() {
        return listCompanyRepository.findAll();
    }
    @GetMapping("/get-job-graphics")
    public List<JobGraphic> getAllJobGraphic() {
        return jobGraphicRepository.findAll();
    }
    @GetMapping("/get-occupations")
    public List<Occupation> getOccupations() {
        return occupationRepository.findAll();
    }

    @GetMapping("/get-education-level")
    public List<EducationLevel> getEducationLevels() {
        return educationLevelRepository.findAll();
    }

    @GetMapping("/get-universities")
    public List<University> getUniversities() {
        return universityRepository.findAll();
    }

}

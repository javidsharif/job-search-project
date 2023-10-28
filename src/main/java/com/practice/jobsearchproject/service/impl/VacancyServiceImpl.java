package com.practice.jobsearchproject.service.impl;

import com.practice.jobsearchproject.config.security.service.CustomUserDetails;
import com.practice.jobsearchproject.exception.NotFoundException;
import com.practice.jobsearchproject.model.dto.request.VacancyRequestDto;
import com.practice.jobsearchproject.model.dto.response.DetailedPublicVacancyResponseDto;
import com.practice.jobsearchproject.model.dto.response.DetailedVacancyResponseDto;
import com.practice.jobsearchproject.model.dto.response.PublicVacancyResponseDto;
import com.practice.jobsearchproject.model.dto.response.VacancyResponseDto;
import com.practice.jobsearchproject.model.entity.Company;
import com.practice.jobsearchproject.model.entity.Vacancy;
import com.practice.jobsearchproject.model.mapper.*;
import com.practice.jobsearchproject.repository.*;
import com.practice.jobsearchproject.service.VacancyService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@RequiredArgsConstructor
@Slf4j
@Transactional
public class VacancyServiceImpl implements VacancyService {
    private final VacancyRepository vacancyRepository;
    private final VacancyMapper vacancyMapper;
    private final CommunicationToolRepository communicationToolRepository;
    private final CategoryRepository categoryRepository;
    private final CityRepository cityRepository;
    private final CurrencyRepository currencyRepository;
    private final JobTypeRepository jobTypeRepository;
    private final SpecialKnowledgeRepository specialKnowledgeRepository;
    private final EducationLevelRepository educationLevelRepository;
    private final UserAuthenticationRepository userAuthenticationRepository;
    private final StatusRepository statusRepository;
    private final CityMapper cityMapper;
    private final JobTypeMapper jobTypeMapper;
    private final SpecialKnowledgeMapper specialKnowledgeMapper;
    private final CommunicationToolMapper communicationToolMapper;
    private final CategoryMapper categoryMapper;
    private final CurrencyMapper currencyMapper;
    private final EducationLevelMapper educationLevelMapper;

    @Override
    public List<PublicVacancyResponseDto> getAllVacancies() {
        log.info("Fetching all public vacancies");
        return vacancyRepository.findAll()
                .stream()
                .filter(vacancy -> vacancy.isPostVacancy() && vacancy.getStatus().getId() == 2)
                .map(vacancy -> {
                    PublicVacancyResponseDto dto = vacancyMapper.convertToPublicVacancyResponseDto(vacancy);
                    dto.setCityResponseDtos(cityMapper.convertToCityResponseDtos(vacancy.getCities()));
                    dto.setJobTypeResponseDtos(jobTypeMapper.convertToJobTypeResponseDtos(vacancy.getJobTypes()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public DetailedPublicVacancyResponseDto getPublicVacancyById(long id) {
        Vacancy vacancy = vacancyRepository.findById(id)
                .stream()
                .filter(v -> v.isPostVacancy() && v.getStatus().getId() == 2)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Vacancy not found!"));
        vacancy.setViewNumber(vacancy.getViewNumber() + 1);
        vacancyRepository.save(vacancy);
        DetailedPublicVacancyResponseDto dto = vacancyMapper.convertToDetailedPublicVacancyResponseDto(vacancy);
        dto.setCityResponseDtos(cityMapper.convertToCityResponseDtos(vacancy.getCities()));
        dto.setJobTypeResponseDtos(jobTypeMapper.convertToJobTypeResponseDtos(vacancy.getJobTypes()));
        dto.setSpecialKnowledgeResponseDtos(specialKnowledgeMapper.convertToSpecialKnowledgeResponseDtos(vacancy.getSpecialKnowledge()));
        dto.setCategoryResponseDto(categoryMapper.convertToCategoryResponseDto(vacancy.getCategory()));
        dto.setEducationLevelResponseDto(educationLevelMapper.convertToEducationLevelResponseDto(vacancy.getEducationLevel()));
        log.info("Getting the vacancy {}", id);
        return dto;
    }

    @Override
    public DetailedVacancyResponseDto getVacancyById(long id, CustomUserDetails companyDetails) {
        Company authenticatedCompany = companyDetails.getUserAuthentication().getCompany();
        List<Vacancy> vacancies = vacancyRepository.findAllByCompany(authenticatedCompany);
        Vacancy vacancy = vacancies
                .stream()
                .filter(v -> v.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Vacancy not found!"));
        DetailedVacancyResponseDto dto = vacancyMapper.convertToDetailedVacancyResponseDto(vacancy);
        dto.setCityResponseDtos(cityMapper.convertToCityResponseDtos(vacancy.getCities()));
        dto.setJobTypeResponseDtos(jobTypeMapper.convertToJobTypeResponseDtos(vacancy.getJobTypes()));
        dto.setSpecialKnowledgeResponseDtos(specialKnowledgeMapper.convertToSpecialKnowledgeResponseDtos(vacancy.getSpecialKnowledge()));
        dto.setCategoryResponseDto(categoryMapper.convertToCategoryResponseDto(vacancy.getCategory()));
        dto.setCommunicationToolResponseDto(communicationToolMapper.convertToCommunicationToolResponseDto(vacancy.getCommunicationTool()));
        dto.setCurrencyResponseDto(currencyMapper.convertToCurrencyResponseDto(vacancy.getCurrency()));
        dto.setEducationLevelResponseDto(educationLevelMapper.convertToEducationLevelResponseDto(vacancy.getEducationLevel()));
        log.info("Getting the vacancy {}", id);
        return dto;
    }

    @Override
    public List<VacancyResponseDto> getAllVacanciesByCompany(CustomUserDetails companyDetails) {
        Company authenticatedCompany = companyDetails.getUserAuthentication().getCompany();
        List<Vacancy> vacancies = vacancyRepository.findAllByCompany(authenticatedCompany);
        log.info("Fetching all vacancies of the company");
        return vacancies.stream()
                .map(vacancyMapper::convertToVacancyResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void createVacancy(VacancyRequestDto vacancyRequestDto, CustomUserDetails companyDetails) {
        Company authenticatedCompany = companyDetails.getUserAuthentication().getCompany();
        Vacancy vacancy = getVacancy(vacancyRequestDto);
        if (vacancyRequestDto.getEmail() == null) {
            vacancy.setEmail(companyDetails.getUsername());
        }
        if (vacancyRequestDto.getForApply() == null) {
            vacancy.setForApply(companyDetails.getUsername());
        }
        vacancy.setCompany(authenticatedCompany);
        vacancyRepository.save(vacancy);
        log.info("Creating new vacancy {}", vacancy.getVacancyName());
    }

    @Override
    public void updateVacancy(long vacancyId, VacancyRequestDto vacancyRequestDto, CustomUserDetails companyDetails) {
        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElseThrow(() -> new NotFoundException("Vacancy not found!"));
        fillVacancy(vacancyRequestDto, vacancy);
        vacancyRepository.save(vacancy);
        log.info("Updating the vacancy {}", vacancy.getVacancyName());
    }

    @Override
    public void deleteVacancy(long vacancyId) {
        if (vacancyRepository.findById(vacancyId).isPresent()) {
            vacancyRepository.deleteById(vacancyId);
        }
        log.info("Deleting the vacancy {}", vacancyId);
    }

    private Vacancy getVacancy(VacancyRequestDto vacancyRequestDto) {
        return Vacancy.builder()
                .applicantName(vacancyRequestDto.getApplicantName())
                .telephone(vacancyRequestDto.getTelephone())
                .email(vacancyRequestDto.getEmail())
                .communicationTool(communicationToolRepository.findCommunicationToolById(vacancyRequestDto.getCommunicationToolId()))
                .vacancyName(vacancyRequestDto.getVacancyName())
                .category(categoryRepository.findCategoryById(vacancyRequestDto.getCategoryId()))
                .isInternship(vacancyRequestDto.isInternship())
                .cities(
                        vacancyRequestDto.getCityIds() != null
                                ? cityRepository.findCityByIdIn(vacancyRequestDto.getCityIds())
                                : null
                )
                .address(vacancyRequestDto.getAddress())
                .fromSalary(vacancyRequestDto.getFromSalary())
                .toSalary(vacancyRequestDto.getToSalary())
                .currency(currencyRepository.findCurrencyById(vacancyRequestDto.getCurrencyId()))
                .jobTypes(
                        vacancyRequestDto.getJobTypeIds() != null
                                ? jobTypeRepository.findJobTypeByIdIn(vacancyRequestDto.getJobTypeIds())
                                : null
                )
                .expiryDate(vacancyRequestDto.getExpiryDate())
                .specialKnowledge(
                        vacancyRequestDto.getSpecialKnowledgeIds() != null
                                ? specialKnowledgeRepository.findSpecialKnowledgeByIdIn(vacancyRequestDto.getSpecialKnowledgeIds())
                                : null
                )
                .textOfVacancy(vacancyRequestDto.getTextOfVacancy())
                .specialRequirements(vacancyRequestDto.getSpecialRequirements())
                .forApply(vacancyRequestDto.getForApply())
                .minimalWorkExperience(vacancyRequestDto.getMinimalWorkExperience())
                .educationLevel(educationLevelRepository.findEducationLevelById(vacancyRequestDto.getEducationLevelId()))
                .noteContactAtVacancy(vacancyRequestDto.isNoteContactAtVacancy())
                .postVacancy(vacancyRequestDto.isPostVacancy())
                .createdAt(LocalDate.now())
                .viewNumber(0)
                .status(statusRepository.getReferenceById(1))
                .build();
    }

    private void fillVacancy(VacancyRequestDto vacancyRequestDto, Vacancy vacancy) {
        vacancy.setApplicantName(vacancyRequestDto.getApplicantName());
        vacancy.setTelephone(vacancyRequestDto.getTelephone());
        vacancy.setEmail(vacancyRequestDto.getEmail());
        vacancy.setCommunicationTool(communicationToolRepository.getReferenceById(vacancyRequestDto.getCommunicationToolId()));
        vacancy.setVacancyName(vacancyRequestDto.getVacancyName());
        vacancy.setCategory(categoryRepository.getReferenceById(vacancyRequestDto.getCategoryId()));
        vacancy.setInternship(vacancyRequestDto.isInternship());
        vacancy.setCities(cityRepository.findCityByIdIn(vacancyRequestDto.getCityIds()));
        vacancy.setAddress(vacancyRequestDto.getAddress());
        vacancy.setFromSalary(vacancyRequestDto.getFromSalary());
        vacancy.setToSalary(vacancyRequestDto.getToSalary());
        vacancy.setCurrency(currencyRepository.getReferenceById(vacancyRequestDto.getCurrencyId()));
        vacancy.setJobTypes(jobTypeRepository.findJobTypeByIdIn(vacancyRequestDto.getJobTypeIds()));
        vacancy.setExpiryDate(vacancyRequestDto.getExpiryDate());
        vacancy.setSpecialKnowledge(specialKnowledgeRepository.findSpecialKnowledgeByIdIn(vacancyRequestDto.getSpecialKnowledgeIds()));
        vacancy.setTextOfVacancy(vacancyRequestDto.getTextOfVacancy());
        vacancy.setSpecialRequirements(vacancyRequestDto.getSpecialRequirements());
        vacancy.setForApply(vacancyRequestDto.getForApply());
        vacancy.setMinimalWorkExperience(vacancyRequestDto.getMinimalWorkExperience());
        vacancy.setEducationLevel(educationLevelRepository.getReferenceById(vacancyRequestDto.getEducationLevelId()));
        vacancy.setNoteContactAtVacancy(vacancyRequestDto.isNoteContactAtVacancy());
        vacancy.setPostVacancy(vacancyRequestDto.isPostVacancy());
        vacancy.setStatus(statusRepository.getReferenceById(1));
    }
}

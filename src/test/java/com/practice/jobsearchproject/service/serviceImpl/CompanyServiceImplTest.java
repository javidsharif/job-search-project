package com.practice.jobsearchproject.service.serviceImpl;

import com.practice.jobsearchproject.exception.AlreadyExistsException;
import com.practice.jobsearchproject.exception.NotFoundException;
import com.practice.jobsearchproject.exception.PasswordException;
import com.practice.jobsearchproject.model.CustomUserDetails;
import com.practice.jobsearchproject.model.dto.CompanyDto;
import com.practice.jobsearchproject.model.dto.request.CompanyRequestDto;
import com.practice.jobsearchproject.model.dto.response.CompanyResponseDto;
import com.practice.jobsearchproject.model.entity.Company;
import com.practice.jobsearchproject.model.entity.Role;
import com.practice.jobsearchproject.model.entity.UserAuthentication;
import com.practice.jobsearchproject.model.mapper.CompanyMapper;
import com.practice.jobsearchproject.repository.CompanyRepository;
import com.practice.jobsearchproject.repository.UserAuthenticationRepository;
import com.practice.jobsearchproject.service.RoleService;
import com.practice.jobsearchproject.service.impl.CompanyServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanyServiceImplTest {
    @Mock
    CompanyRepository companyRepository;
    @Mock
    CompanyMapper companyMapper;
    @InjectMocks
    CompanyServiceImpl companyService;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    RoleService roleService;
    @Mock
    UserAuthenticationRepository userAuthRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(companyRepository.findAll()).thenReturn(new ArrayList<>());
    }

    @Test
    @DisplayName("Test CompanyServiceImpl.getAllCompanies()")
    void testGetAllCompanies() {
        List<Company> mockCompanies = new ArrayList<>();
        mockCompanies.add(Company.builder().id(1L).name("Company 1").telephone("+994 55 545 16 92")
                .cvEmail("cavidan.niyazali@gmail.com").information("cavidan.niyazali")
                .createdAt(LocalDateTime.of(2023, 7, 27, 12, 0)).photoUrl("photo1").city("Baku")
                .role(new Role("User")).foundationDate(LocalDate.of(1999, 8, 16))
                .fieldOfActivity("1").numberOfEmployees(17).address("Baku")
                .siteOfCompany("company1@mail.ru").instagramProfileLink("instagram1")
                .linkedinProfileLink("linkedin1").twitterProfileLink("twitter1")
                .facebookProfileLink("facebook1").build());
        mockCompanies.add(Company.builder().id(2L).name("Company 2").telephone("+994 55 555 55 55")
                .cvEmail("canpo_niyazali@mail.ru").information("canpo_niyazali")
                .createdAt(LocalDateTime.of(2023, 7, 27, 12, 0)).photoUrl("photo2").city("London")
                .role(new Role("User")).foundationDate(LocalDate.of(2000, 2, 22))
                .fieldOfActivity("2").numberOfEmployees(20).address("London")
                .siteOfCompany("company2@mail.ru").instagramProfileLink("instagram2")
                .linkedinProfileLink("linkedin2").twitterProfileLink("twitter2")
                .facebookProfileLink("facebook2").build());

        Mockito.when(companyRepository.findAll()).thenReturn(mockCompanies);

        CompanyResponseDto responseDto1 = CompanyResponseDto.builder().name("Company 1")
                .telephone("+994 55 545 16 92").cvEmail("cavidan.niyazali@gmail.com")
                .information("cavidan.niyazali").createdAt(LocalDateTime.of(2023, 7, 27, 12, 0))
                .photoUrl("photo1").city("Baku").fieldOfActivity("1").numberOfEmployees(17)
                .address("Baku").siteOfCompany("company1@mail.ru").instagramProfileLink("instagram1")
                .linkedinProfileLink("linkedin1").twitterProfileLink("twitter1")
                .facebookProfileLink("facebook1").build();
        CompanyResponseDto responseDto2 = CompanyResponseDto.builder().name("Company 2")
                .telephone("+994 55 555 55 55").cvEmail("canpo_niyazali@mail.ru")
                .information("canpo_niyazali").createdAt(LocalDateTime.of(2023, 7, 27, 12, 0))
                .photoUrl("photo2").city("London").fieldOfActivity("2").numberOfEmployees(20)
                .address("London").siteOfCompany("company2@mail.ru").instagramProfileLink("instagram2")
                .linkedinProfileLink("linkedin2").twitterProfileLink("twitter2")
                .facebookProfileLink("facebook2").build();

        Mockito.when(companyMapper.convertToCompanyResponseDto(Mockito.any(Company.class)))
                .thenReturn(responseDto1, responseDto2);

        List<CompanyResponseDto> result = companyService.getAllCompanies();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Company 1", result.get(0).getName());
        Assertions.assertEquals("+994 55 545 16 92", result.get(0).getTelephone());
        Assertions.assertEquals("cavidan.niyazali@gmail.com", result.get(0).getCvEmail());
        Assertions.assertEquals("cavidan.niyazali", result.get(0).getInformation());
        Assertions.assertEquals(LocalDateTime.of(2023, 7, 27, 12, 0), result.get(0).getCreatedAt());
//        Assertions.assertNull(result.get(0).getPhotoUrl());
        Assertions.assertEquals("photo1", result.get(0).getPhotoUrl());
        Assertions.assertEquals("Baku", result.get(0).getCity());
//        Assertions.assertNull(result.get(0).getFieldOfActivity());
        Assertions.assertEquals("1", result.get(0).getFieldOfActivity());
        Assertions.assertEquals(17, result.get(0).getNumberOfEmployees());
//        Assertions.assertNull(result.get(0).getAddress());
        Assertions.assertEquals("Baku", result.get(0).getAddress());
        Assertions.assertEquals("company1@mail.ru", result.get(0).getSiteOfCompany());
//        Assertions.assertNull(result.get(0).getLinkedinProfileLink());
        Assertions.assertEquals("linkedin1", result.get(0).getLinkedinProfileLink());
//        Assertions.assertNull(result.get(0).getFacebookProfileLink());
        Assertions.assertEquals("facebook1", result.get(0).getFacebookProfileLink());
//        Assertions.assertNull(result.get(0).getInstagramProfileLink());
        Assertions.assertEquals("instagram1", result.get(0).getInstagramProfileLink());
//        Assertions.assertNull(result.get(0).getTwitterProfileLink());
        Assertions.assertEquals("twitter1", result.get(0).getTwitterProfileLink());

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Company 2", result.get(1).getName());
        Assertions.assertEquals("+994 55 555 55 55", result.get(1).getTelephone());
        Assertions.assertEquals("canpo_niyazali@mail.ru", result.get(1).getCvEmail());
        Assertions.assertEquals("canpo_niyazali", result.get(1).getInformation());
        Assertions.assertEquals(LocalDateTime.of(2023, 7, 27, 12, 0), result.get(1).getCreatedAt());
//        Assertions.assertNull(result.get(1).getPhotoUrl());
        Assertions.assertEquals("photo2", result.get(1).getPhotoUrl());
        Assertions.assertEquals("London", result.get(1).getCity());
//        Assertions.assertNull(result.get(1).getFieldOfActivity());
        Assertions.assertEquals("2", result.get(1).getFieldOfActivity());
        Assertions.assertEquals(20, result.get(1).getNumberOfEmployees());
//        Assertions.assertNull(result.get(1).getAddress());
        Assertions.assertEquals("London", result.get(1).getAddress());
        Assertions.assertEquals("company2@mail.ru", result.get(1).getSiteOfCompany());
//        Assertions.assertNull(result.get(1).getLinkedinProfileLink());
        Assertions.assertEquals("linkedin2", result.get(1).getLinkedinProfileLink());
//        Assertions.assertNull(result.get(1).getFacebookProfileLink());
        Assertions.assertEquals("facebook2", result.get(1).getFacebookProfileLink());
//        Assertions.assertNull(result.get(1).getInstagramProfileLink());
        Assertions.assertEquals("instagram2", result.get(1).getInstagramProfileLink());
//        Assertions.assertNull(result.get(1).getTwitterProfileLink());
        Assertions.assertEquals("twitter2", result.get(1).getTwitterProfileLink());

        Mockito.verify(companyRepository, Mockito.times(1)).findAll();
        Mockito.verify(companyMapper, Mockito.times(2))
                .convertToCompanyResponseDto(Mockito.any(Company.class));
    }


    @Test
    @DisplayName("Test CompanyServiceImpl.createCompany() - AlreadyExistsException")
    void testCreateCompany_AlreadyExists() {
        Mockito.when(userAuthRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(new UserAuthentication()));

        Role role = new Role("USER");
        Mockito.when(roleService.findByName(Mockito.anyString())).thenReturn(role);

        CompanyRequestDto requestDto = new CompanyRequestDto();

        requestDto.setEmail("test@gmail.com");
        requestDto.setPassword("password");
        requestDto.setConfirmPassword("password");

        Assertions.assertThrows(AlreadyExistsException.class, () -> companyService.createCompany(requestDto));
        Mockito.verify(companyRepository, Mockito.never()).save(Mockito.any(Company.class));
        Mockito.verify(userAuthRepository, Mockito.never()).save(Mockito.any(UserAuthentication.class));
    }

    @Test
    @DisplayName("Test CompanyServiceImpl.createCompany() - Successful")
    void testCreateCompany_Successful() {
        Mockito.when(userAuthRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        Role role = new Role("USER");
        Mockito.when(roleService.findByName(Mockito.anyString())).thenReturn(role);

        CompanyRequestDto requestDto = new CompanyRequestDto();

        requestDto.setEmail("test@gmail.com");
        requestDto.setPassword("password");
        requestDto.setConfirmPassword("password");

        Assertions.assertDoesNotThrow(() -> companyService.createCompany(requestDto));

        Mockito.verify(companyRepository, Mockito.times(1)).save(Mockito.any(Company.class));
        Mockito.verify(userAuthRepository, Mockito.times(1)).save(Mockito.any(UserAuthentication.class));
    }

    @Test
    @DisplayName("Test CompanyServiceImpl.updateCompany() - When Valid CompanyDto")
    public void updateCompany_whenValidCompanyDto() {
        CompanyDto companyDto = CompanyDto.builder().name("Cavidan")
                .telephone("+994 55 555 55 55").cvEmail("test@gmail.com").information("information")
                .email("test@gmail.com").build();

        String newPassword = "newPassword";
        companyDto.setPassword(newPassword);
        companyDto.setConfirmPassword(newPassword);

        UserAuthentication userAuthentication = new UserAuthentication();
        userAuthentication.setId(1L);
        userAuthentication.setEmail("tset@gmail.com");
        userAuthentication.setPassword(newPassword);
        userAuthentication.setCompany(Company.builder().name(companyDto.getName())
                .telephone(companyDto.getTelephone()).cvEmail(companyDto.getCvEmail())
                .information(companyDto.getInformation()).build());

        CustomUserDetails customUserDetails = new CustomUserDetails(userAuthentication);

        Company authenticatedCompany = customUserDetails.getUserAuthentication().getCompany();
        authenticatedCompany.setUserAuthentication(userAuthentication);
        authenticatedCompany.getUserAuthentication().setCompany(authenticatedCompany);
        authenticatedCompany.getUserAuthentication().setEmail("test@gmail.com");
//        userAuthentication.setCompany(authenticatedCompany);
        Mockito.when(userAuthRepository.findByEmail(customUserDetails.getUsername())).thenReturn(Optional.of(authenticatedCompany.getUserAuthentication()));

        Mockito.when(companyRepository.save(Mockito.any(Company.class))).thenReturn(authenticatedCompany);
        Mockito.when(userAuthRepository.findByEmail("cavidan@gmail.com")).thenReturn(Optional.empty());

        companyService.updateCompany(companyDto, customUserDetails);
        Mockito.verify(companyRepository, Mockito.times(1)).save(authenticatedCompany);
    }

    @Test
    @DisplayName("Test CompanyServiceImpl.testUpdateCompany() - When Password Confirmation Mismatch")
    void testUpdateCompany_whenPasswordConfirmationMismatch() {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setPassword("defaultPassword");
        companyDto.setConfirmPassword("differentPassword");

        UserAuthentication userAuthentication = new UserAuthentication();
        userAuthentication.setId(1L);
        userAuthentication.setEmail("tset@gmail.com");
        userAuthentication.setPassword("newPassword");
        userAuthentication.setCompany(Company.builder().name(companyDto.getName())
                .telephone(companyDto.getTelephone()).cvEmail(companyDto.getCvEmail())
                .information(companyDto.getInformation()).build());
        CustomUserDetails userDetails = new CustomUserDetails(userAuthentication);

        Company authenticatedCompany = userDetails.getUserAuthentication().getCompany();
        authenticatedCompany.setUserAuthentication(userAuthentication);
        authenticatedCompany.getUserAuthentication().setCompany(authenticatedCompany);
        authenticatedCompany.getUserAuthentication().setEmail("test@gmail.com");
        Mockito.when(userAuthRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(authenticatedCompany.getUserAuthentication()));

        Assertions.assertThrows(PasswordException.class, () -> companyService.updateCompany(companyDto, userDetails));
    }

    @Test
    @DisplayName("Test CompanyServiceImpl.testUpdateCompany() - When Not Found Exception")
    void testUpdateCompany_whenNotFoundException() {
        CompanyDto companyDto = new CompanyDto();

        UserAuthentication userAuthentication = new UserAuthentication();
        userAuthentication.setId(1L);
        userAuthentication.setEmail("tset@gmail.com");
        userAuthentication.setPassword("newPassword");
        userAuthentication.setCompany(Company.builder().name(companyDto.getName())
                .telephone(companyDto.getTelephone()).cvEmail(companyDto.getCvEmail())
                .information(companyDto.getInformation()).build());
        CustomUserDetails userDetails = new CustomUserDetails(userAuthentication);
        Company authenticatedCompany = userDetails.getUserAuthentication().getCompany();
        authenticatedCompany.setUserAuthentication(userAuthentication);
        authenticatedCompany.getUserAuthentication().setCompany(authenticatedCompany);
        Mockito.when(userAuthRepository.findByEmail(companyDto.getEmail())).thenThrow(NotFoundException.class);

        Assertions.assertThrows(NotFoundException.class, () -> companyService.updateCompany(companyDto, userDetails));
    }

    @Test
    @DisplayName("Test CompanyServiceImpl.testUpdateCompany() - When Email Already Exists")
    void testUpdateCompany_whenEmailAlreadyExists() {
        String existingEmail = "existing@gmail.com";
        CompanyDto companyRequestDto = CompanyDto.builder().name("Cavidan")
                .telephone("+994 55 555 55 55").cvEmail("test@gmail.com").information("information")
                .email(existingEmail).build();

        UserAuthentication userAuthentication = new UserAuthentication();
        userAuthentication.setId(1L);
        userAuthentication.setEmail(existingEmail);
        userAuthentication.setPassword("12345");
        userAuthentication.setCompany(Company.builder().name(companyRequestDto.getName())
                .telephone(companyRequestDto.getTelephone()).cvEmail(companyRequestDto.getCvEmail())
                .information(companyRequestDto.getInformation()).build());
        CustomUserDetails userDetails = new CustomUserDetails(userAuthentication);
        Company authenticatedCompany = userDetails.getUserAuthentication().getCompany();
        authenticatedCompany.setUserAuthentication(userAuthentication);
        authenticatedCompany.getUserAuthentication().setCompany(authenticatedCompany);
        authenticatedCompany.getUserAuthentication().setEmail(existingEmail);
        Mockito.when(userAuthRepository.findByEmail(existingEmail)).thenReturn(Optional.of(authenticatedCompany.getUserAuthentication()));
        Mockito.when(userAuthRepository.findByEmail(companyRequestDto.getEmail())).thenThrow(AlreadyExistsException.class);
        Assertions.assertThrows(AlreadyExistsException.class, () -> companyService.updateCompany(companyRequestDto, userDetails));
    }
}
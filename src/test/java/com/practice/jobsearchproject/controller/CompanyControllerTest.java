package com.practice.jobsearchproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.jobsearchproject.exception.AlreadyExistsException;
import com.practice.jobsearchproject.exception.PasswordException;
import com.practice.jobsearchproject.model.CustomUserDetails;
import com.practice.jobsearchproject.model.dto.CompanyDto;
import com.practice.jobsearchproject.model.dto.request.CompanyRequestDto;
import com.practice.jobsearchproject.model.dto.response.CompanyResponseDto;
import com.practice.jobsearchproject.model.entity.UserAuthentication;
import com.practice.jobsearchproject.service.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class CompanyControllerTest {
    @Mock
    private CompanyService companyService;
    @InjectMocks
    private CompanyController companyController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(companyService.getAllCompanies()).thenReturn(new ArrayList<>());
    }
    @Test
    @DisplayName("Test getAllCompanies() with data")
    void testGetAllCompaniesWithData() throws Exception {
        List<CompanyResponseDto> companyList = Arrays.asList(
                CompanyResponseDto.builder().name("Company 1").telephone("+994 55 123 45 67").cvEmail("company1@gmail.com").build(),
                CompanyResponseDto.builder().name("Company 2").telephone("+994 50 987 65 43").cvEmail("company2@gmail.com").build()
        );

        Mockito.when(companyService.getAllCompanies()).thenReturn(companyList);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(companyController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/companies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(companyList.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Company 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].telephone").value("+994 55 123 45 67"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cvEmail").value("company1@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Company 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].telephone").value("+994 50 987 65 43"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].cvEmail").value("company2@gmail.com"));
    }
    @Test
    @DisplayName("Test getAllCompanies() with no data")
    void testGetAllCompaniesWithNoData() throws Exception {
        List<CompanyResponseDto> companyList = Arrays.asList();

        Mockito.when(companyService.getAllCompanies()).thenReturn(companyList);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(companyController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/companies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(0));
    }
    @Test
    @DisplayName("Test createCompany() with valid data")
    void testCreateCompanyWithValidData() throws Exception {
        CompanyRequestDto companyRequestDto = new CompanyRequestDto();
        companyRequestDto.setEmail("cavidan.niyazali@gmail.com");
        companyRequestDto.setPassword("12345");
        companyRequestDto.setConfirmPassword("12345");

        Mockito.doNothing().when(companyService).createCompany(Mockito.any(CompanyRequestDto.class));

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(companyController).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(companyRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    @DisplayName("Test createCompany() with invalid data")
    void testCreateCompanyWithInvalidData() throws Exception {
        CompanyRequestDto companyRequestDto = new CompanyRequestDto();
        companyRequestDto.setEmail("test@gmail.com");
        companyRequestDto.setPassword("password");
        companyRequestDto.setConfirmPassword("different_password");

        Mockito.doThrow(new PasswordException("Password is wrong")).when(companyService).createCompany(Mockito.any(CompanyRequestDto.class));

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(companyController).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(companyRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    @DisplayName("Test createCompany() with existing email")
    void testCreateCompanyWithExistingEmail() throws Exception {
        CompanyRequestDto companyRequestDto = new CompanyRequestDto();
        companyRequestDto.setEmail("test@gmail.com");
        companyRequestDto.setPassword("password");
        companyRequestDto.setConfirmPassword("password");

        Mockito.doThrow(new AlreadyExistsException("email already exists")).when(companyService).createCompany(Mockito.any(CompanyRequestDto.class));

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(companyController).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(companyRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    @DisplayName("Test updateCompany() with valid data")
    void testUpdateUserWithValidData() throws Exception {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setName("Updated Company");
        companyDto.setEmail("updated_email@gmail.com");
        companyDto.setPassword("newPassword");
        companyDto.setConfirmPassword("newPassword");
        companyDto.setTelephone("+994 55 123 45 67");
        companyDto.setCvEmail("updated_cv_email@gmail.com");
        companyDto.setInformation("Updated company information");
        companyDto.setPhotoUrl("updated_photo_url");
        companyDto.setCity("Updated City");
        companyDto.setFieldOfActivity("Updated Field");

        UserAuthentication userAuthentication = new UserAuthentication();
        userAuthentication.setEmail("test@gmail.com");
        userAuthentication.setPassword("oldPassword");
        CustomUserDetails userDetails = new CustomUserDetails(userAuthentication);

        companyService.updateCompany(Mockito.eq(companyDto), Mockito.eq(userDetails));

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(companyController).build();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/companies")
                        .with(request -> {
                            request.setUserPrincipal(request.getUserPrincipal());
                            return request;
                        })
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(companyDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test updateCompany() with invalid data")
    void testUpdateUserWithInvalidData() throws Exception {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setName("Updated Company");
        companyDto.setEmail("invalid_email");
        companyDto.setPassword("newPassword");
        companyDto.setConfirmPassword("newPassword");

        UserAuthentication userAuthentication = new UserAuthentication();
        userAuthentication.setEmail("test@gmail.com");
        userAuthentication.setPassword("oldPassword");
        CustomUserDetails userDetails = new CustomUserDetails(userAuthentication);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(companyController).build();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/companies")
//                        .with(user(userDetails))
                        .with(request -> {
                            request.setUserPrincipal(request.getUserPrincipal());
                            return request;
                        })
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(companyDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}

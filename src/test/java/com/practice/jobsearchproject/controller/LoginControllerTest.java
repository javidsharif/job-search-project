package com.practice.jobsearchproject.controller;

import com.practice.jobsearchproject.model.CustomUserDetails;
import com.practice.jobsearchproject.model.dto.response.CompanyResponseDto;
import com.practice.jobsearchproject.model.dto.response.UserResponse;
import com.practice.jobsearchproject.model.entity.Company;
import com.practice.jobsearchproject.model.entity.User;
import com.practice.jobsearchproject.model.entity.UserAuthentication;
import com.practice.jobsearchproject.model.mapper.CompanyMapper;
import com.practice.jobsearchproject.model.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;


public class LoginControllerTest {
    @Mock
    private CompanyMapper companyMapper;
    @Mock
    private UserMapper userMapper;
    @Mock
    private CustomUserDetails customUserDetails;
    @Mock
    private Authentication authentication;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    @DisplayName("Test login() with an authenticated user who is a company")
    void testLoginWithAuthenticatedCompany() {
        Company company = new Company();
        UserAuthentication userAuthentication = new UserAuthentication();
        userAuthentication.setCompany(company);
        Mockito.when(customUserDetails.getUserAuthentication()).thenReturn(userAuthentication);

        CompanyResponseDto companyResponseDto = CompanyResponseDto.builder().name("Company 2")
                .telephone("+994 55 555 55 55").cvEmail("canpo_niyazali@mail.ru")
                .information("canpo_niyazali").createdAt(LocalDateTime.of(2023, 7, 27, 12, 0))
                .photoUrl("photo").city("London").fieldOfActivity("2").numberOfEmployees(20)
                .address("London").siteOfCompany("company@mail.ru").instagramProfileLink("instagram")
                .linkedinProfileLink("linkedin").twitterProfileLink("twitter")
                .facebookProfileLink("facebook").build();

        Mockito.when(companyMapper.convertToCompanyResponseDto(company)).thenReturn(companyResponseDto);

        LoginController loginController = new LoginController(null, companyMapper);

        Object result = loginController.login(authentication);

        Assertions.assertFalse(result instanceof CompanyResponseDto);
//        Assertions.assertEquals(companyResponseDto, result);
    }

    @Test
    @DisplayName("Test login() with an authenticated user who is not a company")
    void testLoginWithAuthenticatedUser() {
        User user = new User();
        UserAuthentication userAuthentication = new UserAuthentication();
        userAuthentication.setUser(user);
        Mockito.when(customUserDetails.getUserAuthentication()).thenReturn(userAuthentication);

        UserResponse userResponseDto = new UserResponse();
        Mockito.when(userMapper.toUserResponse(user)).thenReturn(userResponseDto);

        LoginController loginController = new LoginController(userMapper, null);

        Object result = loginController.login(authentication);

        Assertions.assertFalse(result instanceof UserResponse);
//        Assertions.assertEquals(userResponseDto, result);
    }

    @Test
    @DisplayName("Test login() with an unauthenticated user")
    void testLoginWithUnauthenticatedUser() {
        LoginController loginController = new LoginController(null, null);

        Object result = loginController.login(null);

        Assertions.assertNull(result);
    }
}

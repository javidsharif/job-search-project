//package com.practice.jobsearchproject.controller;
//
//import com.practice.jobsearchproject.config.security.service.CustomUserDetails;
//import com.practice.jobsearchproject.model.dto.RoleDto;
//import com.practice.jobsearchproject.model.dto.response.CompanyResponseDto;
//import com.practice.jobsearchproject.model.dto.response.UserResponse;
//import com.practice.jobsearchproject.model.entity.Company;
//import com.practice.jobsearchproject.model.entity.User;
//import com.practice.jobsearchproject.model.entity.UserAuthentication;
//import com.practice.jobsearchproject.model.mapper.CompanyMapper;
//import com.practice.jobsearchproject.model.mapper.UserMapper;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.time.LocalDateTime;
//import java.util.Date;
//
//public class LoginControllerTest {
//    @Mock
//    private CompanyMapper companyMapper;
//    @Mock
//    private UserMapper userMapper;
//    @Mock
//    private CustomUserDetails customUserDetails;
//    @InjectMocks
//    private LoginController loginController;
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//    @Test
//    @DisplayName("Test login() with an authenticated user who is a company")
//    void testLoginWithAuthenticatedCompany() {
//        Company company = new Company();
//        company.setId(1L);
//        company.setCvEmail("default");
//        CompanyResponseDto responseDto = buildDefaultResponseDto();
//        UserAuthentication userAuthentication = new UserAuthentication();
//
//        userAuthentication.setEmail("default@default.com");
//        userAuthentication.setPassword(encoder().encode("default"));
//        userAuthentication.setCompany(company);
//        company.setUserAuthentication(userAuthentication);
//        CustomUserDetails userDetails = new CustomUserDetails(userAuthentication);
//        userDetails.getUserAuthentication().setCompany(company);
//        Mockito.when(customUserDetails.getUserAuthentication()).thenReturn(userAuthentication);
//        Mockito.when(companyMapper.convertToCompanyResponseDto(company)).thenReturn(responseDto);
//
//        Authentication authentication = new CustomAuthentication(userDetails);
//        Object result = loginController.auth(authentication);
//        Assertions.assertEquals(responseDto, result);
//    }
//    @Test
//    @DisplayName("Test login() with an authenticated user who is not a company")
//    void testLoginWithAuthenticatedUser() {
//        User user = new User();
//        user.setId(1L);
//        UserResponse responseDto = buildResponseUserDto();
//        UserAuthentication userAuthentication = new UserAuthentication();
//
//        userAuthentication.setEmail("default@default.com");
//        userAuthentication.setPassword(encoder().encode("default"));
//        userAuthentication.setUser(user);
//        user.setUserAuthentication(userAuthentication);
//        CustomUserDetails userDetails = new CustomUserDetails(userAuthentication);
//        userDetails.getUserAuthentication().setUser(user);
//        Mockito.when(customUserDetails.getUserAuthentication()).thenReturn(userAuthentication);
//        Mockito.when(userMapper.toUserResponse(user)).thenReturn(responseDto);
//
//        Authentication authentication = new CustomAuthentication(userDetails);
//        Object result = loginController.auth(authentication);
//        Assertions.assertEquals(responseDto, result);
//    }
//
//    @Test
//    @DisplayName("Test login() with an unauthenticated user")
//    void testLoginWithUnauthenticatedUser() {
//        LoginController loginController = new LoginController(null, null);
//
//        Object result = loginController.auth(null);
//
//        Assertions.assertNull(result);
//    }
//    private UserResponse buildResponseUserDto() {
//        UserResponse userResponse = new UserResponse();
//        userResponse.setName("default");
//        userResponse.setCity("default");
//        userResponse.setRole(buildRoleDto());
//        userResponse.setGender("default");
//        userResponse.setPhone("+99411111111");
//        userResponse.setDateOfBirth(new Date());
//        userResponse.setSurname("default");
//        return userResponse;
//    }
//
//    private RoleDto buildRoleDto() {
//        RoleDto roleDto = new RoleDto();
//        roleDto.setId(1L);
//        roleDto.setName("USER");
//        return roleDto;
//    }
//    private CompanyResponseDto buildDefaultResponseDto() {
//        return CompanyResponseDto.builder().name("Company 2")
//                .telephone("+994 55 555 55 55").cvEmail("canpo_niyazali@mail.ru")
//                .information("canpo_niyazali").createdAt(LocalDateTime.of(2023, 7, 27, 12, 0))
//                .photoUrl("photo").city("London").fieldOfActivity("2").numberOfEmployees(20)
//                .address("London").siteOfCompany("company@mail.ru").instagramProfileLink("instagram")
//                .linkedinProfileLink("linkedin").twitterProfileLink("twitter")
//                .facebookProfileLink("facebook").build();
//    }
//    private PasswordEncoder encoder() {
//        return new BCryptPasswordEncoder();
//    }
//}

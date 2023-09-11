//package com.practice.jobsearchproject.service;
//
//import com.practice.jobsearchproject.config.security.SecurityConfig;
//import com.practice.jobsearchproject.config.security.jwt.JwtTokenUtil;
//import com.practice.jobsearchproject.config.security.service.CustomUserDetails;
//import com.practice.jobsearchproject.config.security.service.CustomUserDetailsService;
//import com.practice.jobsearchproject.model.dto.ResetPasswordDto;
//import com.practice.jobsearchproject.model.dto.request.AuthenticationRequest;
//import com.practice.jobsearchproject.model.dto.response.AuthenticationResponse;
//import com.practice.jobsearchproject.model.dto.response.CompanyAuthenticationResponse;
//import com.practice.jobsearchproject.model.dto.response.CompanyResponseDto;
//import com.practice.jobsearchproject.model.entity.*;
//import com.practice.jobsearchproject.model.mapper.CompanyMapper;
//import com.practice.jobsearchproject.model.mapper.UserMapper;
//import com.practice.jobsearchproject.repository.UserAuthenticationRepository;
//import com.practice.jobsearchproject.repository.VerificationTokenRepository;
//import com.practice.jobsearchproject.service.impl.UserAuthenticationServiceImpl;
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import com.practice.jobsearchproject.model.dto.RoleDto;
//import com.practice.jobsearchproject.model.dto.response.UserResponse;
//import com.practice.jobsearchproject.model.entity.Company;
//import com.practice.jobsearchproject.exception.NotFoundException;
//import com.practice.jobsearchproject.exception.PasswordException;
//import com.practice.jobsearchproject.model.entity.UserAuthentication;
//import com.practice.jobsearchproject.model.entity.VerificationToken;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import java.time.LocalDateTime;
//import java.util.Date;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//@Import(SecurityConfig.class)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class UserAuthenticationServiceImplTest {
//    @MockBean
//    private UserAuthenticationRepository userAuthRepository;
//    @Mock
//    private PasswordEncoder passwordEncoder;
//    @InjectMocks
//    private UserAuthenticationServiceImpl userAuthService;
//    @MockBean
//    private AuthenticationManager authenticationManager;
//    @MockBean
//    private JwtTokenUtil jwtTokenUtil;
//    @MockBean
//    private CustomUserDetailsService customUserDetailsService;
//    @MockBean
//    private VerificationTokenRepository verificationTokenRepository;
//    @MockBean
//    private UserMapper userMapper;
//    @MockBean
//    private CompanyMapper companyMapper;
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void testSetPassword_Success() {
//        ResetPasswordDto resetPasswordDto = new ResetPasswordDto("test@example.com", "newPassword", "newPassword");
//        UserAuthentication userAuthentication = new UserAuthentication();
//        userAuthentication.setEmail("test@example.com");
//        userAuthentication.setPassword("oldPassword");
//
//        Mockito.when(userAuthRepository.findByEmail("test@example.com")).thenReturn(Optional.of(userAuthentication));
//        Mockito.when(passwordEncoder.encode("newPassword")).thenReturn("encodedPassword");
//
//        String result = userAuthService.setPassword(resetPasswordDto);
//
//        Assertions.assertEquals("Password updated!", result);
//        assertEquals("encodedNewPassword", userAuthentication.getPassword());
//        Mockito.verify(userAuthRepository, Mockito.times(1)).save(userAuthentication);
//    }
//
//    @Test
//    public void testSetPassword_UserNotFound() {
//        ResetPasswordDto resetPasswordDto = new ResetPasswordDto("test@example.com", "newPassword", "newPassword");
//        userAuthService.setPassword(resetPasswordDto);
//
//        Mockito.when(userAuthRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
//        Assertions.assertThrows(NotFoundException.class, () -> userAuthService.setPassword(resetPasswordDto));
//        verify(passwordEncoder, never()).encode(anyString());
//    }
//
//    @Test
//    public void testSetPassword_PasswordMismatch() {
//        ResetPasswordDto resetPasswordDto = new ResetPasswordDto("test@example.com", "newPassword", "differentPassword");
//        UserAuthentication userAuthentication = new UserAuthentication();
//        userAuthentication.setEmail("test@example.com");
//        userAuthService.setPassword(resetPasswordDto);
//
//        Mockito.when(userAuthRepository.findByEmail(resetPasswordDto.getPassword())).thenReturn(Optional.of(userAuthentication));
//        Assertions.assertThrows(PasswordException.class, () -> userAuthService.setPassword(resetPasswordDto));
//        Mockito.verify(passwordEncoder, never()).encode(anyString());
//    }
//
//    @Test
//    @DisplayName("Test login() with an authenticated user who is a company")
//    public void testLoginWithAuthenticatedCompany_Success() throws Exception {
//        AuthenticationRequest authenticationRequest = new AuthenticationRequest("test@example.com", "password");
//        UserAuthentication userAuthentication = new UserAuthentication();
//        userAuthentication.setEmail("test@example.com");
//        userAuthentication.setPassword("encodedPassword");
//        VerificationToken verificationToken = new VerificationToken(userAuthentication);
//
//        Company company = new Company();
//        company.setName("Demo");
//        company.setCity("Demo");
//        userAuthentication.setCompany(company);
//        company.setUserAuthentication(userAuthentication);
//        CompanyResponseDto responseDto = buildDefaultResponseDto();
//
////        UserDetails userDetails = new org.springframework.security.core.userdetails.User("test@example.com", "encodedPassword", new ArrayList<>());
//        CustomUserDetails userDetails = new CustomUserDetails(userAuthentication);
//        userDetails.getUserAuthentication().setCompany(company);
//        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "encodedPassword");
//
//        Mockito.when(userDetails.getUserAuthentication()).thenReturn(userAuthentication);
//        Mockito.when(userAuthRepository.findByEmail("test@example.com")).thenReturn(Optional.of(userAuthentication));
//        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);
////        Mockito.when(authenticationManager.authenticate(any())).thenReturn(null);
////        Mockito.when(jwtTokenUtil.generateToken(any(UserDetails.class))).thenReturn("testToken");
//        Mockito.when(jwtTokenUtil.generateToken(userDetails)).thenReturn("generatedToken");
//        Mockito.when(verificationTokenRepository.findByUserAuthentication(userAuthentication)).thenReturn(Optional.of(new VerificationToken()));
//        Mockito.when(companyMapper.convertToCompanyResponseDto(company)).thenReturn(responseDto);
//
//        AuthenticationResponse result = userAuthService.login(authenticationRequest);
//
//        Assertions.assertNotNull(result);
//        Assertions.assertTrue(result instanceof CompanyAuthenticationResponse);
//        Assertions.assertEquals("generatedToken", ((CompanyAuthenticationResponse) result).getToken());
//    }
//
//    @Test
//    @DisplayName("Test login() with an authenticated user who is not a company")
//    void testLoginWithAuthenticatedUser() throws Exception {
//        AuthenticationRequest authenticationRequest = new AuthenticationRequest("test@example.com", "password");
//        UserAuthentication userAuthentication = new UserAuthentication();
//        userAuthentication.setEmail("test@example.com");
//        userAuthentication.setPassword("encodedPassword");
//        User user = new User();
//        user.setName("Demo");
//        user.setCity("Demo");
//        userAuthentication.setUser(user);
//        user.setUserAuthentication(userAuthentication);
//        UserResponse responseDto = buildResponseUserDto();
////        UserDetails userDetails = new org.springframework.security.core.userdetails.User("test@example.com", "encodedPassword", new ArrayList<>());
//
//        CustomUserDetails userDetails = new CustomUserDetails(userAuthentication);
//        userDetails.getUserAuthentication().setUser(user);
//        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "encodedPassword");
//
//        Mockito.when(userDetails.getUserAuthentication()).thenReturn(userAuthentication);
//        Mockito.when(userAuthRepository.findByEmail("test@example.com")).thenReturn(Optional.of(userAuthentication));
//        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);
//        Mockito.when(jwtTokenUtil.generateToken(userDetails)).thenReturn("generatedToken");
//        Mockito.when(verificationTokenRepository.findByUserAuthentication(userAuthentication)).thenReturn(Optional.of(new VerificationToken()));
//        Mockito.when(userMapper.toUserResponse(user)).thenReturn(responseDto);
//
//        AuthenticationResponse result = userAuthService.login(authenticationRequest);
//
//        Assertions.assertNotNull(result);
//        Assertions.assertTrue(result instanceof CompanyAuthenticationResponse);
//        Assertions.assertEquals("generatedToken", ((CompanyAuthenticationResponse) result).getToken());
//    }
//
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
//
//    private CompanyResponseDto buildDefaultResponseDto() {
//        return CompanyResponseDto.builder().name("Company 2")
//                .telephone("+994 55 555 55 55").cvEmail("canpo_niyazali@mail.ru")
//                .information("canpo_niyazali").createdAt(LocalDateTime.of(2023, 7, 27, 12, 0))
//                .photoUrl("photo").city("London").fieldOfActivity("2").numberOfEmployees(20)
//                .address("London").siteOfCompany("company@mail.ru").instagramProfileLink("instagram")
//                .linkedinProfileLink("linkedin").twitterProfileLink("twitter")
//                .facebookProfileLink("facebook").build();
//    }
//
//    private PasswordEncoder encoder() {
//        return new BCryptPasswordEncoder();
//    }
//}

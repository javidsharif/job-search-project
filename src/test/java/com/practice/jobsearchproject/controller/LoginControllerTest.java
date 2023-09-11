package com.practice.jobsearchproject.controller;

import com.practice.jobsearchproject.model.dto.request.AuthenticationRequest;
import com.practice.jobsearchproject.model.dto.response.CompanyAuthenticationResponse;
import com.practice.jobsearchproject.model.dto.response.CompanyResponseDto;
import com.practice.jobsearchproject.service.UserAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;


import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@WebMvcTest(controllers = LoginController.class)
@RequiredArgsConstructor
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = {LoginController.class})
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserAuthenticationService userAuthenticationService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser(username = "testuser", password = "testPassword", roles = {"USER", "ADMIN"})
    void testAuth() throws Exception {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail("testuser");
        authenticationRequest.setPassword("testpassword");

        CompanyAuthenticationResponse authenticationResponse = new CompanyAuthenticationResponse();
        authenticationResponse.setToken("testtoken");
        authenticationResponse.setCompany(new CompanyResponseDto());

        when(userAuthenticationService.login(any(AuthenticationRequest.class)))
                .thenReturn(authenticationResponse);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/auth/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authenticationRequest));

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        CompanyAuthenticationResponse response = objectMapper.readValue(jsonResponse, CompanyAuthenticationResponse.class);
        Assertions.assertNotNull(response);
        Assertions.assertEquals("testtoken", response.getToken());
    }
}

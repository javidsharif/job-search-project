package com.practice.jobsearchproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.jobsearchproject.model.dto.ResetPasswordDto;
import com.practice.jobsearchproject.service.UserAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@AutoConfigureMockMvc
@WebMvcTest(controllers = UserAuthenticationController.class)
@RequiredArgsConstructor
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = {UserAuthenticationController.class})
public class UserAuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserAuthenticationService userAuthenticationService;

    @Test
    @WithMockUser(username = "testuser", password = "testPassword", roles = {"USER", "ADMIN"})
    public void testUpdatePassword() throws Exception {
        ResetPasswordDto resetPasswordDto = new ResetPasswordDto();
        resetPasswordDto.setEmail("testuser");
        resetPasswordDto.setPassword("newPassword");
        resetPasswordDto.setConfirmPassword("newPassword");

        String expectedResponse = "Password updated successfully";
        Mockito.when(userAuthenticationService.setPassword(resetPasswordDto)).thenReturn(expectedResponse);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/auth/reset-password")
                        .content(new ObjectMapper().writeValueAsString(resetPasswordDto))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(expectedResponse, content);
    }
}


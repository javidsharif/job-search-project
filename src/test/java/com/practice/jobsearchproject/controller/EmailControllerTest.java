package com.practice.jobsearchproject.controller;

import com.practice.jobsearchproject.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
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

@WebMvcTest(controllers = EmailController.class)
@ContextConfiguration(classes = {EmailController.class})
@RequiredArgsConstructor
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailService emailService;

    @Test
    @WithMockUser(username = "test@example.com", password = "testPassword", roles = {"USER", "ADMIN"})
    void testForgotPassword() throws Exception {
        String email = "test@example.com";

        String expectedResponse = "Reset password email sent successfully";
        Mockito.when(emailService.createResetPasswordEmail(email)).thenReturn(expectedResponse);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/mail/forgot-password")
                        .param("email", email)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals(expectedResponse, actualResponse);
    }
}
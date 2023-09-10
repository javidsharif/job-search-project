//package com.practice.jobsearchproject.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.practice.jobsearchproject.model.dto.request.AuthenticationRequest;
//import com.practice.jobsearchproject.model.dto.response.AuthenticationResponse;
//import com.practice.jobsearchproject.service.UserAuthenticationService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.RequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//
//
////@SpringBootTest
////@AutoConfigureMockMvc
////public class LoginControllerTest {
////
////    @Autowired
////    private MockMvc mockMvc;
////
////    @MockBean
////    UserAuthenticationService userAuthenticationService;
////
////    @Test
////    public void testLogin() throws Exception {
//////        String requestJson = "{\"username\":\"cavidan.niyazali@gmail.com\",\"password\":\"12345\"}";
////        AuthenticationRequest authenticationRequest=AuthenticationRequest
////                .builder()
////                .email("cavidan.niyazali@gmail.com")
////                .password("12345")
////                .build();
////        Mockito.when(userAuthenticationService.login(authenticationRequest)).thenThrow(new Exception());
////        RequestBuilder requestBuilder = MockMvcRequestBuilders
////                .post("/api/auth/login")
////                .with(csrf());
////        MvcResult mvcResultGet = mockMvc.perform(requestBuilder).andReturn();
////
//////                .content(requestJson)
//////                .contentType(MediaType.APPLICATION_JSON);
//////                        .accept(MediaType.APPLICATION_JSON))
//////                .andExpect(MockMvcResultMatchers.status().isOk())
//////                .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());
////    }
////}
//@WebMvcTest(LoginController.class)
//public class LoginControllerTest {
//
//    @InjectMocks
//    private LoginController loginController;
//
//    @Mock
//    private UserAuthenticationService userAuthenticationService;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void testLogin() throws Exception {
//        AuthenticationRequest request = new AuthenticationRequest("username", "password");
//        AuthenticationResponse response = new AuthenticationResponse();
//
//        Mockito.when(userAuthenticationService.login(Mockito.any(AuthenticationRequest.class))).thenReturn(response);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(request)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("token"));
//    }
//
//    private String asJsonString(Object obj) {
//        try {
//            return new ObjectMapper().writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
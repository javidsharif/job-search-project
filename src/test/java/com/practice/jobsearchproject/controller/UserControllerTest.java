//package com.practice.jobsearchproject.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.practice.jobsearchproject.exception.AlreadyExistsException;
//import com.practice.jobsearchproject.exception.PasswordException;
//import com.practice.jobsearchproject.model.dto.request.UserRequestDto;
//import com.practice.jobsearchproject.model.dto.response.UserResponse;
//import com.practice.jobsearchproject.model.entity.Role;
//import com.practice.jobsearchproject.model.entity.User;
//import com.practice.jobsearchproject.model.entity.UserAuthentication;
//import com.practice.jobsearchproject.model.mapper.UserMapperImpl;
//import com.practice.jobsearchproject.service.FileService;
//import com.practice.jobsearchproject.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.RequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import java.time.LocalDateTime;
//import java.util.Date;
//import java.util.List;
//import java.util.Objects;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.when;
//import static org.mockito.Mockito.doThrow;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(controllers = UserController.class)
//@Import(UserMapperImpl.class)
//@WithMockUser
//@RequiredArgsConstructor
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class UserControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @MockBean
//    private UserService userService;
//
//    @MockBean
//    private FileService fileService;
//
//    private static Long DEFAULT_ID = 1L;
//
//    private static String DEFAULT_ADMIN_NAME = "ADMIN";
//    private static String DEFAULT_USER_NAME = "USER";
//
//    private final UserRequestDto DEFAULT_USER_DTO = buildRequestDto();
//    private final User DEFAULT_USER = buildDefaultUser(DEFAULT_USER_DTO);
//
//    private final UserAuthentication DEFAULT_USER_AUTHENTICATION = buildUserAuthentication();
//    private final String DEFAULT_VALUE = "Default";
//
//    private final String DEFAULT_EMAIL = "default@default.com";
//    private Date DEFAULT_DATE = new Date();
//
//    ObjectMapper mapper = new ObjectMapper();
//
//    @Test
//    @Order(1)
//    public void testGetAllUsers() throws Exception {
//        List<User> userList = List.of(buildDefaultUser(buildRequestDto()), buildDefaultUser(buildRequestDto()));
//        when(userService.getAllUsers()).thenReturn(userList);
//        RequestBuilder requestBuilderGet = MockMvcRequestBuilders
//                .get("/api/v1/users")
//                .with(csrf());
//        MvcResult mvcResultGet = mockMvc.perform(requestBuilderGet).andReturn();
//        String responseBodyGet = mvcResultGet.getResponse().getContentAsString();
//        List<UserResponse> list = mapper.readValue(responseBodyGet, List.class);
//        assertEquals(list.size(), 2);
//        assertEquals(HttpStatus.OK.value(), mvcResultGet.getResponse().getStatus());
//    }
//
//    @Test
//    @Order(2)
//    public void testCreateUser_whenValidRequestUserDto() throws Exception {
//        RequestBuilder requestBuilderPost = MockMvcRequestBuilders
//                .multipart("/api/v1/users")
//                .file(new MockMultipartFile("userDto", "", "application/json", mapper.writeValueAsString(DEFAULT_USER_DTO).getBytes()))
//                .file(new MockMultipartFile("file", "", "application/json", "file.jpg".getBytes()))
//                .with(csrf())
//                .accept(MediaType.APPLICATION_JSON_VALUE);
//        MvcResult mvcResultPost = mockMvc.perform(requestBuilderPost).andReturn();
//        assertEquals(HttpStatus.CREATED.value(), mvcResultPost.getResponse().getStatus());
//    }
//
//    @Test
//    @Order(3)
//    public void testCreateUser_whenThrowEmailAlreadyException() throws Exception {
//        UserRequestDto userRequestDto = buildRequestDto();
//        doThrow(new AlreadyExistsException(String.format("email with %s already exists",
//                userRequestDto.getEmail()))).when(userService).createUser(eq(userRequestDto), any());
//        RequestBuilder requestBuilderPost = MockMvcRequestBuilders
//                .multipart("/api/v1/users")
//                .file(new MockMultipartFile("userDto", "", "application/json", mapper.writeValueAsString(userRequestDto).getBytes()))
//                .file(new MockMultipartFile("file", "", "application/json", "file.jpg".getBytes()))
//                .with(csrf())
//                .accept(MediaType.APPLICATION_JSON_VALUE);
//        MvcResult mvcResultPost = mockMvc.perform(requestBuilderPost)
//                .andExpect(status().isBadRequest())
//                .andReturn();
//
//        String expected = String.format("email with %s already exists", DEFAULT_EMAIL);
//        String actualErrorMessage = Objects.requireNonNull(mvcResultPost.getResolvedException()).getMessage();
//        assertEquals(expected, actualErrorMessage);
//        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResultPost.getResponse().getStatus());
//    }
//
//    @Test
//    @Order(4)
//    public void testCreateUser_whenThrowPasswordException() throws Exception {
//        UserRequestDto userRequestDto = buildRequestDto();
//        doThrow(new PasswordException("password is wrong")).when(userService).createUser(eq(userRequestDto), any());
//        RequestBuilder requestBuilderPost = MockMvcRequestBuilders
//                .multipart("/api/v1/users")
//                .file(new MockMultipartFile("userDto", "", "application/json", mapper.writeValueAsString(userRequestDto).getBytes()))
//                .file(new MockMultipartFile("file", "", "application/json", "file.jpg".getBytes()))
//                .with(csrf())
//                .accept(MediaType.APPLICATION_JSON_VALUE);
//        MvcResult mvcResultPost = mockMvc.perform(requestBuilderPost)
//                .andExpect(status().isBadRequest())
//                .andReturn();
//
//        String expected = "password is wrong";
//        String actualErrorMessage = Objects.requireNonNull(mvcResultPost.getResolvedException()).getMessage();
//        assertEquals(expected, actualErrorMessage);
//        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResultPost.getResponse().getStatus());
//    }
//
//    @Test
//    @Order(5)
//    @WithMockUser(username = "testUser", password = "testPassword", roles = {"USER", "ADMIN"})
//    public void testUpdateUser_whenValidUserDtoAndUserDetails() throws Exception {
//        UserRequestDto userRequestDto = buildRequestDto();
//        RequestBuilder requestBuilderPost = MockMvcRequestBuilders
//                .multipart(HttpMethod.PUT,"/api/v1/users")
//                .file(new MockMultipartFile("userDto", "", "application/json", mapper.writeValueAsString(DEFAULT_USER_DTO).getBytes()))
//                .file(new MockMultipartFile("file", "", "application/json", "file.jpg".getBytes()))
//                .with(csrf())
//                .accept(MediaType.APPLICATION_JSON_VALUE);
//        MvcResult mvcResultPut = mockMvc.perform(requestBuilderPost).andReturn();
//        assertEquals(HttpStatus.OK.value(), mvcResultPut.getResponse().getStatus());
//    }
//
//    @Test
//    @Order(6)
//    public void testUpdateUser_whenThrowEmailAlreadyExistsException() throws Exception {
//        UserRequestDto userRequestDto = buildRequestDto();
//        doThrow(new AlreadyExistsException(String.format("email with %s already exists",
//                userRequestDto.getEmail()))).when(userService).updateUser(eq(userRequestDto), any(), any());
//        RequestBuilder requestBuilderPost = MockMvcRequestBuilders
//                .multipart(HttpMethod.PUT,"/api/v1/users")
//                .file(new MockMultipartFile("userDto", "", "application/json", mapper.writeValueAsString(userRequestDto).getBytes()))
//                .file(new MockMultipartFile("file", "", "application/json", "file.jpg".getBytes()))
//                .with(csrf())
//                .accept(MediaType.APPLICATION_JSON_VALUE);
//        MvcResult mvcResultPut = mockMvc.perform(requestBuilderPost)
//                .andExpect(status().isBadRequest())
//                .andReturn();
//
//        String expected = String.format("email with %s already exists", DEFAULT_EMAIL);
//        String actualErrorMessage = Objects.requireNonNull(mvcResultPut.getResolvedException()).getMessage();
//        assertEquals(expected, actualErrorMessage);
//        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResultPut.getResponse().getStatus());
//    }
//
//    @Test
//    @Order(7)
//    public void testUpdateUser_whenThrowPasswordException() throws Exception {
//        UserRequestDto userRequestDto = buildRequestDto();
//        doThrow(new PasswordException("password is wrong")).when(userService).updateUser(eq(userRequestDto), any(), any());
//        RequestBuilder requestBuilderPost = MockMvcRequestBuilders
//                .multipart(HttpMethod.PUT,"/api/v1/users")
//                .file(new MockMultipartFile("userDto", "", "application/json", mapper.writeValueAsString(userRequestDto).getBytes()))
//                .file(new MockMultipartFile("file", "", "application/json", "file.jpg".getBytes()))
//                .with(csrf())
//                .accept(MediaType.APPLICATION_JSON_VALUE);
//        MvcResult mvcResultPut = mockMvc.perform(requestBuilderPost)
//                .andExpect(status().isBadRequest())
//                .andReturn();
//        String expected = "password is wrong";
//        String actualErrorMessage = Objects.requireNonNull(mvcResultPut.getResolvedException()).getMessage();
//        assertEquals(expected, actualErrorMessage);
//        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResultPut.getResponse().getStatus());
//    }
//
//
//    private Role buildRole() {
//        Role role = new Role();
//        role.setId(DEFAULT_ID);
//        role.setName(DEFAULT_USER_NAME);
//        return role;
//    }
//
//    private User buildDefaultUser(UserRequestDto userRequestDto) {
//        return User.builder()
//                .id(DEFAULT_ID)
//                .name(userRequestDto.getName())
//                .surname(userRequestDto.getSurname())
//                .city(userRequestDto.getCity())
//                .gender(userRequestDto.getGender())
//                .dateOfBirth(userRequestDto.getDateOfBirth())
//                .phone(userRequestDto.getPhone())
//                .createdAt(LocalDateTime.now())
//                .role(buildRole())
//                .userAuthentication(DEFAULT_USER_AUTHENTICATION).build();
//    }
//
//    private UserAuthentication buildUserAuthentication() {
//        UserAuthentication userAuthentication = new UserAuthentication();
//        userAuthentication.setId(DEFAULT_ID);
//        userAuthentication.setEmail(DEFAULT_EMAIL);
//        userAuthentication.setPassword(DEFAULT_VALUE);
//        userAuthentication.setUser(DEFAULT_USER);
//        return userAuthentication;
//    }
//
//    private UserRequestDto buildRequestDto() {
//        UserRequestDto userRequestDto = new UserRequestDto();
//        userRequestDto.setName(DEFAULT_VALUE);
//        userRequestDto.setSurname(DEFAULT_VALUE);
//        userRequestDto.setCity(DEFAULT_VALUE);
//        userRequestDto.setEmail(DEFAULT_EMAIL);
//        userRequestDto.setDateOfBirth(DEFAULT_DATE);
//        userRequestDto.setPhone(DEFAULT_VALUE);
//        userRequestDto.setGender(DEFAULT_VALUE);
//        userRequestDto.setPassword(DEFAULT_VALUE);
//        userRequestDto.setConfirmPassword(DEFAULT_VALUE);
//        return userRequestDto;
//    }
//
//}

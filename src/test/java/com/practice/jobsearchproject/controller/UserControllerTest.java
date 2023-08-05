package com.practice.jobsearchproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.jobsearchproject.exception.AlreadyExistsException;
import com.practice.jobsearchproject.exception.PasswordException;
import com.practice.jobsearchproject.model.dto.request.UserRequestDto;
import com.practice.jobsearchproject.model.dto.response.UserResponse;
import com.practice.jobsearchproject.model.entity.Role;
import com.practice.jobsearchproject.model.entity.User;
import com.practice.jobsearchproject.model.entity.UserAuthentication;
import com.practice.jobsearchproject.model.mapper.UserMapperImpl;
import com.practice.jobsearchproject.repository.UserAuthenticationRepository;
import com.practice.jobsearchproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@Import(UserMapperImpl.class)
@WithMockUser
@RequiredArgsConstructor
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserAuthenticationRepository userAuthRepository;
    @MockBean
    private UserService userService;

    private static Long DEFAULT_ID = 1L;

    private static String DEFAULT_ADMIN_NAME = "ADMIN";
    private static String DEFAULT_USER_NAME = "USER";

    private final UserRequestDto DEFAULT_USER_DTO = buildRequestDto();
    private final User DEFAULT_USER = buildDefaultUser(DEFAULT_USER_DTO);

    private final UserAuthentication DEFAULT_USER_AUTHENTICATION = buildUserAuthentication();
    private final String DEFAULT_VALUE = "default";

    private final String DEFAULT_EMAIL = "default@default.com";
    private Date DEFAULT_DATE = new Date();

    ObjectMapper mapper = new ObjectMapper();

    @Test
    @Order(1)
    public void testGetAllUsers() throws Exception {
        List<User> userList = List.of(buildDefaultUser(buildRequestDto()), buildDefaultUser(buildRequestDto()));
        when(userService.getAllUsers()).thenReturn(userList);
        RequestBuilder requestBuilderGet = MockMvcRequestBuilders
                .get("/api/v1/users")
                .with(csrf());
        MvcResult mvcResultGet = mockMvc.perform(requestBuilderGet).andReturn();
        String responseBodyGet = mvcResultGet.getResponse().getContentAsString();
        List<UserResponse> list = mapper.readValue(responseBodyGet, List.class);
        assertEquals(list.size(), 2);
        assertEquals(HttpStatus.OK.value(), mvcResultGet.getResponse().getStatus());
    }

    @Test
    @Order(2)
    public void testCreateUser_whenValidRequestUserDto() throws Exception {
        UserRequestDto requestDto = buildRequestDto();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .content(new ObjectMapper().writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isCreated());
        verify(userService).createUser(requestDto);
    }

    @Test
    @Order(3)
    public void testCreateUser_whenThrowEmailAlreadyException() throws Exception {
        UserRequestDto userRequestDto = buildRequestDto();
        doThrow(new AlreadyExistsException(String.format("email with %s already exists",
                userRequestDto.getEmail()))).when(userService).createUser(userRequestDto);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .content(new ObjectMapper().writeValueAsString(userRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andReturn();

        String expected = String.format("email with %s already exists", DEFAULT_EMAIL);
        String actualErrorMessage = Objects.requireNonNull(mvcResult.getResolvedException()).getMessage();
        assertEquals(expected, actualErrorMessage);
        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    @Order(4)
    public void testCreateUser_whenThrowPasswordException() throws Exception {
        UserRequestDto userRequestDto = buildRequestDto();
        doThrow(new PasswordException("password is wrong")).when(userService).createUser(userRequestDto);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .content(new ObjectMapper().writeValueAsString(userRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andReturn();
        String expected = "password is wrong";
        String actualErrorMessage = Objects.requireNonNull(mvcResult.getResolvedException()).getMessage();
        assertEquals(expected, actualErrorMessage);
        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    @Order(5)
    @WithMockUser(username = "testUser", password = "testPassword", roles = {"USER", "ADMIN"})
    public void testUpdateUser_whenValidUserDtoAndUserDetails() throws Exception {
        UserRequestDto userRequestDto = buildRequestDto();
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/users")
                        .content(new ObjectMapper().writeValueAsString(userRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());
        verify(userService).updateUser(eq(userRequestDto), any());
    }

    @Test
    @Order(6)
    public void testUpdateUser_whenThrowEmailAlreadyExistsException() throws Exception {
        UserRequestDto userRequestDto = buildRequestDto();
        doThrow(new AlreadyExistsException(String.format("email with %s already exists",
                userRequestDto.getEmail()))).when(userService).updateUser(eq(userRequestDto), any());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/users")
                        .content(new ObjectMapper().writeValueAsString(userRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andReturn();

        String expected = String.format("email with %s already exists", DEFAULT_EMAIL);
        String actualErrorMessage = Objects.requireNonNull(mvcResult.getResolvedException()).getMessage();
        assertEquals(expected, actualErrorMessage);
        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    @Order(7)
    public void testUpdateUser_whenThrowPasswordException() throws Exception {
        UserRequestDto userRequestDto = buildRequestDto();
        doThrow(new PasswordException("password is wrong")).when(userService).updateUser(eq(userRequestDto), any());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/users")
                        .content(new ObjectMapper().writeValueAsString(userRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andReturn();
        String expected = "password is wrong";
        String actualErrorMessage = Objects.requireNonNull(mvcResult.getResolvedException()).getMessage();
        assertEquals(expected, actualErrorMessage);
        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }


    private Role buildRole() {
        Role role = new Role();
        role.setId(DEFAULT_ID);
        role.setName(DEFAULT_USER_NAME);
        return role;
    }

    private User buildDefaultUser(UserRequestDto userRequestDto) {
        return User.builder()
                .id(DEFAULT_ID)
                .name(userRequestDto.getName())
                .surname(userRequestDto.getSurname())
                .city(userRequestDto.getCity())
                .gender(userRequestDto.getGender())
                .dateOfBirth(userRequestDto.getDateOfBirth())
                .photoUrl(userRequestDto.getPhotoUrl())
                .phone(userRequestDto.getPhone())
                .createdAt(LocalDateTime.now())
                .role(buildRole())
                .userAuthentication(DEFAULT_USER_AUTHENTICATION).build();
    }

    private UserAuthentication buildUserAuthentication() {
        UserAuthentication userAuthentication = new UserAuthentication();
        userAuthentication.setId(DEFAULT_ID);
        userAuthentication.setEmail(DEFAULT_EMAIL);
        userAuthentication.setPassword(DEFAULT_VALUE);
        userAuthentication.setUser(DEFAULT_USER);
        return userAuthentication;
    }

    private UserRequestDto buildRequestDto() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setName(DEFAULT_VALUE);
        userRequestDto.setSurname(DEFAULT_VALUE);
        userRequestDto.setCity(DEFAULT_VALUE);
        userRequestDto.setEmail(DEFAULT_EMAIL);
        userRequestDto.setDateOfBirth(DEFAULT_DATE);
        userRequestDto.setPhone(DEFAULT_VALUE);
        userRequestDto.setGender(DEFAULT_VALUE);
        userRequestDto.setPassword(DEFAULT_VALUE);
        userRequestDto.setConfirmPassword(DEFAULT_VALUE);
        userRequestDto.setPhotoUrl(DEFAULT_VALUE);
        return userRequestDto;
    }

}

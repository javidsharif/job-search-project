package com.practice.jobsearchproject.service;

import com.practice.jobsearchproject.config.SecurityConfig;
import com.practice.jobsearchproject.exception.AlreadyExistsException;
import com.practice.jobsearchproject.exception.NotFoundException;
import com.practice.jobsearchproject.exception.PasswordException;
import com.practice.jobsearchproject.model.CustomUserDetails;
import com.practice.jobsearchproject.model.dto.request.UserRequestDto;
import com.practice.jobsearchproject.model.entity.Role;
import com.practice.jobsearchproject.model.entity.User;
import com.practice.jobsearchproject.model.entity.UserAuthentication;
import com.practice.jobsearchproject.repository.UserAuthenticationRepository;
import com.practice.jobsearchproject.repository.UserRepository;
import com.practice.jobsearchproject.service.impl.UserServiceImpl;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.Captor;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;

@ExtendWith(MockitoExtension.class)
@Import(SecurityConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {
    private final static Long DEFAULT_ID = 1L;

    private final static String DEFAULT_ADMIN_NAME = "ADMIN";
    private final static String DEFAULT_USER_NAME = "USER";

    private final UserRequestDto DEFAULT_USER_DTO = buildRequestDto();
    private final User DEFAULT_USER = buildDefaultUser(DEFAULT_USER_DTO);

    private final UserAuthentication DEFAULT_USER_AUTHENTICATION = buildUserAuthentication();
    private final String DEFAULT_VALUE = "default";

    private final String DEFAULT_EMAIL = "default@default.com";
    private final Date DEFAULT_DATE = new Date();

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private PasswordEncoder encoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private UserAuthenticationRepository userAuthRepository;

    @Captor
    private ArgumentCaptor<User> saveCaptor;

    @Test
    @Order(1)
    public void createUser_whenValidUserRequestDto() {
        var userRequestDto = buildRequestDto();
        when(userAuthRepository.findByEmail(userRequestDto.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer((i) -> i.getArguments()[0]);
        when(encoder.encode(anyString())).then(i -> encoder().encode((CharSequence) i.getArguments()[0]));

        when(roleService.findByName(DEFAULT_USER_NAME)).thenReturn(buildRole());
        userService.createUser(userRequestDto);

        verify(userRepository, times(1)).save(saveCaptor.capture());
        User capturedUser = saveCaptor.getValue();
        assertTrue(encoder().matches(userRequestDto.getPassword(), capturedUser.getUserAuthentication().getPassword()));
        verify(userAuthRepository, times(1)).findByEmail(userRequestDto.getEmail());
        verify(userAuthRepository, times(1)).save(any(UserAuthentication.class));
    }

    @Test
    @Order(2)
    public void createUser_whenThrowEmailAlreadyExists() {
        when(userAuthRepository.findByEmail(DEFAULT_EMAIL)).thenReturn(Optional.of(DEFAULT_USER_AUTHENTICATION));
        UserRequestDto userRequestDto = DEFAULT_USER_DTO;

        assertThrows(AlreadyExistsException.class, () -> userService.createUser(userRequestDto));
    }

    @Test
    @Order(3)
    public void createUser_whenThrowPasswordException() {
        UserRequestDto userRequestDto = buildRequestDto();
        userRequestDto.setConfirmPassword("newDefault");
        when(userAuthRepository.findByEmail(userRequestDto.getEmail())).thenReturn(Optional.empty());

        assertThrows(PasswordException.class, () -> userService.createUser(userRequestDto));

        verify(userAuthRepository, times(1)).findByEmail(userRequestDto.getEmail());
        verify(userAuthRepository, never()).save(any(UserAuthentication.class));
        verify(roleService, never()).findByName(anyString());
    }

    @Test
    @Order(4)
    public void updateUser_whenValidUserRequestDto() {
        UserRequestDto userRequestDto = buildRequestDto();
        String newPassword = "newPassword";
        userRequestDto.setPassword(newPassword);
        userRequestDto.setConfirmPassword(newPassword);

        CustomUserDetails userDetails = new CustomUserDetails(buildDefaultUser(userRequestDto).getUserAuthentication());
        userDetails.getUserAuthentication().getUser().setUserAuthentication(userDetails.getUserAuthentication());
        User authenticatedUser = userDetails.getUserAuthentication().getUser();
        authenticatedUser.getUserAuthentication().setUser(authenticatedUser);
        authenticatedUser.getUserAuthentication().setEmail("mimi@test.com");
        when(userAuthRepository.findByEmail("mimi@test.com")).thenReturn(Optional.of(authenticatedUser.getUserAuthentication()));
        when(encoder.encode("newPassword")).thenReturn(encoder().encode("newPassword"));
        when(userRepository.save(any(User.class))).thenReturn(authenticatedUser);

        userService.updateUser(userRequestDto, userDetails);
        verify(userRepository, times(1)).save(authenticatedUser);
    }

    @Test
    @Order(5)
    void testUpdateUser_whenPasswordConfirmationMismatch() {
        UserRequestDto userDto = buildRequestDto();
        userDto.setPassword("defaultPassword");
        userDto.setConfirmPassword("differentPassword");

        CustomUserDetails userDetails = new CustomUserDetails(buildDefaultUser(userDto).getUserAuthentication());
        userDetails.getUserAuthentication().getUser().setUserAuthentication(userDetails.getUserAuthentication());
        var authenticatedUser = userDetails.getUserAuthentication().getUser();
        authenticatedUser.getUserAuthentication().setUser(authenticatedUser);
        authenticatedUser.getUserAuthentication().setEmail("kolya@test.com");
        when(userAuthRepository.findByEmail("kolya@test.com")).thenReturn(Optional.of(authenticatedUser.getUserAuthentication()));

        assertThrows(PasswordException.class, () -> userService.updateUser(userDto, userDetails));
    }

    @Test
    @Order(6)
    void testUpdateUser_whenNotFoundException() {
        UserRequestDto userDto = buildRequestDto();

        CustomUserDetails userDetails = new CustomUserDetails(buildDefaultUser(userDto).getUserAuthentication());
        userDetails.getUserAuthentication().getUser().setUserAuthentication(userDetails.getUserAuthentication());
        var authenticatedUser = userDetails.getUserAuthentication().getUser();
        authenticatedUser.getUserAuthentication().setUser(authenticatedUser);
        when(userAuthRepository.findByEmail(userDto.getEmail())).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> userService.updateUser(userDto, userDetails));
    }

    @Test
    @Order(6)
    void testUpdateUser_whenEmailAlreadyExists() {
        String existingEmail = "default@default.com";
        UserRequestDto userRequestDto = buildRequestDto();

        CustomUserDetails userDetails = new CustomUserDetails(buildDefaultUser(userRequestDto).getUserAuthentication());
        userDetails.getUserAuthentication().getUser().setUserAuthentication(userDetails.getUserAuthentication());
        var authenticatedUser = userDetails.getUserAuthentication().getUser();
        authenticatedUser.getUserAuthentication().setUser(authenticatedUser);
        authenticatedUser.getUserAuthentication().setEmail(existingEmail);
        when(userAuthRepository.findByEmail(existingEmail)).thenReturn(Optional.of(authenticatedUser.getUserAuthentication()));
        when(userAuthRepository.findByEmail(existingEmail)).thenThrow(AlreadyExistsException.class);
        assertThrows(AlreadyExistsException.class, () -> userService.updateUser(userRequestDto, userDetails));
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

    private PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}

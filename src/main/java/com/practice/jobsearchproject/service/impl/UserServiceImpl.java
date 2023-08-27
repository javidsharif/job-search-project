package com.practice.jobsearchproject.service.impl;

import com.practice.jobsearchproject.config.JwtTokenUtil;
import com.practice.jobsearchproject.exception.AlreadyExistsException;
import com.practice.jobsearchproject.exception.NotFoundException;
import com.practice.jobsearchproject.exception.PasswordException;
import com.practice.jobsearchproject.model.CustomUserDetails;
import com.practice.jobsearchproject.model.dto.request.UserRequestDto;
//import com.practice.jobsearchproject.model.dto.response.AuthenticationResponse;
//import com.practice.jobsearchproject.model.dto.response.UserAuthenticationResponse;
import com.practice.jobsearchproject.model.dto.response.AuthenticationResponse;
import com.practice.jobsearchproject.model.dto.response.UserAuthenticationResponse;
import com.practice.jobsearchproject.model.entity.User;
import com.practice.jobsearchproject.model.entity.UserAuthentication;
import com.practice.jobsearchproject.model.mapper.UserMapper;
import com.practice.jobsearchproject.repository.UserAuthenticationRepository;
import com.practice.jobsearchproject.repository.UserRepository;
import com.practice.jobsearchproject.service.RoleService;
import com.practice.jobsearchproject.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserMapper userMapper;
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserAuthenticationRepository userAuthRepository;

    @Override
    public List<User> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public AuthenticationResponse createUser(UserRequestDto userDto) {
        if (userAuthRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new AlreadyExistsException("email already exists");
        }
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            throw new PasswordException("Password is wrong");
        }
        User user = getUser(userDto);
        UserAuthentication userAuth = getUserAuthentication(userDto.getEmail(), userDto.getPassword(), user);
        user.setUserAuthentication(userAuth);
        userAuth.setUser(user);
        user.setRole(roleService.findByName("USER"));
        save(user);
        userAuthRepository.save(userAuth);
        log.info("Creating new user {}", user.getName());

        final UserDetails userDetails = customUserDetailsService
                .loadUserByUsername(userDto.getEmail());
        final var token = jwtTokenUtil.generateToken(userDetails);
        return UserAuthenticationResponse.builder().token(token).user(userMapper.toUserResponse(user)).build();
    }

    @Override
    public void updateUser(UserRequestDto userDto, CustomUserDetails userDetails) {
        String newPassword = userDto.getPassword();
        String confirmedPassword = userDto.getConfirmPassword();
        User authenticatedUser = findByEmail(userDetails.getUsername()).getUser();
        if (!newPassword.equals(confirmedPassword)) {
            throw new PasswordException("The password confirmation does not match.");
        }
        if (!newPassword.equals(authenticatedUser.getUserAuthentication().getPassword())) {
            authenticatedUser.getUserAuthentication().setPassword(passwordEncoder.encode(newPassword));
        }
        fillUser(userDto, authenticatedUser);
        save(authenticatedUser);
        log.info("Updating the user {}", authenticatedUser.getName());
    }

    private User getUser(UserRequestDto userDto) {
        return User.builder()
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .city(userDto.getCity())
                .dateOfBirth(userDto.getDateOfBirth())
                .gender(userDto.getGender())
                .phone(userDto.getPhone())
                .photoUrl(userDto.getPhotoUrl())
                .createdAt(LocalDateTime.now())
                .build();
    }

    private UserAuthentication getUserAuthentication(String email, String password, User user) {
        return new UserAuthentication(email, passwordEncoder.encode(password), user);
    }

    private void fillUser(UserRequestDto userDto, User authenticatedUser) {
        authenticatedUser.setName(userDto.getName());
        authenticatedUser.setSurname(userDto.getSurname());
        authenticatedUser.setCity(userDto.getCity());
        authenticatedUser.setDateOfBirth(userDto.getDateOfBirth());
        if (!authenticatedUser.getUserAuthentication().getEmail().equals(userDto.getEmail())) {
            authenticatedUser.getUserAuthentication().setEmail(userDto.getEmail());
        }
        if (!authenticatedUser.getUserAuthentication().getPassword().equals(userDto.getPassword())) {
            authenticatedUser.getUserAuthentication().setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        authenticatedUser.setPhone(userDto.getPhone());
        authenticatedUser.setGender(userDto.getGender());
        authenticatedUser.setPhotoUrl(userDto.getPhotoUrl());
    }

    public UserAuthentication findByEmail(String email) {
        return userAuthRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(String.format("email with %s not found", email)));
    }

    public void save(User user) {
        userRepository.save(user);
    }
}

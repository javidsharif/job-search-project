package com.practice.jobsearchproject.service.impl;

import com.practice.jobsearchproject.exception.AlreadyExistsException;
import com.practice.jobsearchproject.exception.NotFoundException;
import com.practice.jobsearchproject.exception.PasswordException;
import com.practice.jobsearchproject.model.CustomUserDetails;
import com.practice.jobsearchproject.model.dto.request.UserRequestDto;
import com.practice.jobsearchproject.model.entity.User;
import com.practice.jobsearchproject.model.entity.UserAuthentication;
import com.practice.jobsearchproject.repository.UserAuthenticationRepository;
import com.practice.jobsearchproject.repository.UserRepository;
import com.practice.jobsearchproject.service.RoleService;
import com.practice.jobsearchproject.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    private final UserAuthenticationRepository userAuthRepository;
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    @Override
    public void createUser(UserRequestDto userDto) {
        if (userAuthRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new AlreadyExistsException("email already exists");
        }
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            throw new PasswordException("Password is wrong");
        }
        if (userDto.getPassword() == null || userDto.getPassword().isEmpty() || userDto.getPassword().isBlank()) {
            throw new PasswordException("Password cannot be null");
        }
        User user = getUser(userDto);
        UserAuthentication userAuth = getUserAuthentication(userDto.getEmail(), userDto.getPassword(), user);
        user.setUserAuthentication(userAuth);
        userAuth.setUser(user);
        user.setRole(roleService.findByName("USER"));
        save(user);
        userAuthRepository.save(userAuth);
    }

    @Override
    public void updateUser(UserRequestDto userDto, CustomUserDetails userDetails) {

    }
//    @Override
//    public void updateUser(UserRequestDto userDto, CustomUserDetails userDetails) {
//        String newPassword = userDto.getPassword();
//        String confirmedPassword = userDto.getConfirmPassword();
//        User authenticatedUser = findByEmail(userDetails.getUsername());
//        if (!newPassword.equals(confirmedPassword)) {
//            throw new PasswordException("The password confirmation does not match.");
//        }
//        if (!newPassword.equals(authenticatedUser.getPassword())) {
//            authenticatedUser.setPassword(passwordEncoder.encode(newPassword));
//        }
//        String dtoEmail = userDto.getEmail();
//        if (!dtoEmail.equals(authenticatedUser.getEmail())) {
//            if (userRepository.findByEmail(dtoEmail).isPresent()) {
//                throw new AlreadyExistsException(
//                        String.format("email with %s already exists", dtoEmail));
//            }
//        }
//        fillUser(userDto, authenticatedUser);
//        save(authenticatedUser);
//    }

    private User getUser(UserRequestDto userDto) {
        return User.builder()
//                .password(passwordEncoder.encode(userDto.getPassword()))
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .city(userDto.getCity())
//                .email(userDto.getEmail())
                .dateOfBirth(userDto.getDateOfBirth())
                .gender(userDto.getGender())
                .phone(userDto.getPhone())
                .photoUrl(userDto.getPhotoUrl())
                .createdAt(LocalDateTime.now())
                .build();
    }

    private UserAuthentication getUserAuthentication(String email, String password, User user) {
        return new UserAuthentication(email,passwordEncoder.encode(password),user);
    }

//    private void fillUser(UserRequestDto userDto, User authenticatedUser) {
//        authenticatedUser.setName(userDto.getName());
//        authenticatedUser.setSurname(userDto.getSurname());
//        authenticatedUser.setCity(userDto.getCity());
//        authenticatedUser.setDateOfBirth(userDto.getDateOfBirth());
//        authenticatedUser.setEmail(userDto.getEmail());
//        authenticatedUser.setPhone(userDto.getPhone());
//        authenticatedUser.setGender(userDto.getGender());
//        authenticatedUser.setPhotoUrl(userDto.getPhotoUrl());
//        authenticatedUser.setCreatedAt(LocalDateTime.now());
//    }
//    private User findByEmail(String email) {
//        return userRepository.findByEmail(email)
//                .orElseThrow(() -> new NotFoundException(String.format("email with %s not found", email)));
//    }
    public void save(User user) {
        userRepository.save(user);
    }
}

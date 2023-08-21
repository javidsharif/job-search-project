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
import com.practice.jobsearchproject.service.FileService;
import com.practice.jobsearchproject.service.RoleService;
import com.practice.jobsearchproject.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Data
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final FileService fileService;
    private final UserAuthenticationRepository userAuthRepository;
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void createUser(UserRequestDto userDto, MultipartFile file) throws IOException {
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
        if(file != null && !file.isEmpty()) {
            fileService.uploadFile(file, user);
        }
        save(user);
        userAuthRepository.save(userAuth);
    }

    @Override
    public void updateUser(UserRequestDto userDto, MultipartFile file, CustomUserDetails userDetails) throws IOException {
        String newPassword = userDto.getPassword();
        String confirmedPassword = userDto.getConfirmPassword();
        User authenticatedUser = findByEmail(userDetails.getUsername()).getUser();
        if (!newPassword.equals(confirmedPassword)) {
            throw new PasswordException("The password confirmation does not match.");
        }
        if (!newPassword.equals(authenticatedUser.getUserAuthentication().getPassword())) {
            authenticatedUser.getUserAuthentication().setPassword(passwordEncoder.encode(newPassword));
        }
        if (file != null && !file.isEmpty()) {
            Optional.ofNullable(authenticatedUser.getPhotoUrl())
                    .filter(photoUrl -> !photoUrl.isEmpty())
                    .map(photoUrl -> photoUrl.substring(photoUrl.lastIndexOf('/') + 1))
                    .ifPresent(fileService::deleteFile);

            fileService.uploadFile(file, authenticatedUser);
        }
        fillUser(userDto, authenticatedUser);
        save(authenticatedUser);
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
    }

    public UserAuthentication findByEmail(String email) {
        return userAuthRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(String.format("email with %s not found", email)));
    }

    public void save(User user) {
        userRepository.save(user);
    }
}

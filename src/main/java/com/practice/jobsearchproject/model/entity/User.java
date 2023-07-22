package com.practice.jobsearchproject.model.entity;

import com.practice.jobsearchproject.model.dto.request.UserRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "name is mandatory")
    private String name;
    @NotBlank(message = "surname is mandatory")
    private String surname;

    private String password;
    @NotBlank(message = "email is mandatory")
    private String email;

    private Date dateOfBirth;
    @NotBlank(message = "phone is mandatory")
    private String phone;
    private String gender;
    private String city;
    private String photoUrl;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinTable(
            name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Role role;

}

package com.practice.jobsearchproject.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserAuthentication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    @OneToOne(mappedBy = "userAuthentication")
    private VerificationToken verificationToken;

    public UserAuthentication(String email, String password, User user) {
        this.email = email;
        this.password = password;
        this.user = user;
    }

    public UserAuthentication(String email, String password, Company company) {
        this.email = email;
        this.password = password;
        this.company = company;
    }
}

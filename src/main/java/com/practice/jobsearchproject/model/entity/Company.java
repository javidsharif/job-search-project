package com.practice.jobsearchproject.model.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@Entity
@Table(name = "companies")
@AllArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "name is mandatory")
    @Column(name = "name")
    private String name;
    @Column(name = "telephone")
    private String telephone;
    @NotBlank(message = "cv email is mandatory")
    @Column(name = "cv_email")
    private String cvEmail;
    @Column(name = "information")
    private String information;
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "photo_url")
    private String photoUrl;
    @Column(name = "city")
    private String city;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;
    @Column(name = "foundation_date")
    private LocalDate foundationDate;
    @Column(name = "field_of_activity")
    private String fieldOfActivity;
    @Column(name = "number_of_employees")
    private Integer numberOfEmployees;
    @Column(name = "address")
    private String address;
    @Column(name = "site_of_company")
    private String siteOfCompany;
    @Column(name = "facebook_profile_link")
    private String facebookProfileLink;
    @Column(name = "twitter_profile_link")
    private String twitterProfileLink;
    @Column(name = "linkedin_profile_link")
    private String linkedinProfileLink;
    @Column(name = "instagram_profile_link")
    private String instagramProfileLink;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_authentication_id", referencedColumnName = "id")
    private UserAuthentication userAuthentication;

    @OneToMany(mappedBy = "company")
    private List<Vacancy> vacancies;
}

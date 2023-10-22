package com.practice.jobsearchproject.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.practice.jobsearchproject.model.dto.request.ResumeRequestDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameAndSurname;
    private Long count;
    private String phone;
    private String email;
    private String preferredMeansOfCommunication;
    private String position;
    private String aboutUs;
    private String facebookLink;
    private String linkedinLink;
    private String githubLink;
    private String behanceLink;
    private Boolean isPostCv;
    private LocalDateTime updateDate;
    private LocalDateTime registrationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    public void updateResume(ResumeRequestDto requestDto) {
        this.nameAndSurname = requestDto.getNameAndSurname();
        this.phone = requestDto.getPhone();
        this.email = requestDto.getEmail();
        this.preferredMeansOfCommunication = requestDto.getPreferredMeansOfCommunication();
        this.position = requestDto.getPosition();
        this.aboutUs = requestDto.getAboutUs();
        this.facebookLink = requestDto.getFacebookLink();
        this.githubLink = requestDto.getGithubLink();
        this.linkedinLink = requestDto.getLinkedinLink();
        this.behanceLink = requestDto.getBehanceLink();
        this.isPostCv = requestDto.getIsPostCv();
        this.updateDate = LocalDateTime.now();
    }
}

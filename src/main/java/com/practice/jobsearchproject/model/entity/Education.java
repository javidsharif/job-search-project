package com.practice.jobsearchproject.model.entity;

import com.practice.jobsearchproject.model.dto.request.EducationRequest;
import com.practice.jobsearchproject.utills.Updatable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Education implements Updatable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String educationName;
    private Integer level;
    private String faculty;
    private Date dateOfGraduation;
    private String additionalInformation;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Override
    public <T> void update(T request) {
        EducationRequest educationRequest = (EducationRequest) request;
        this.educationName = educationRequest.getEducationName();
        this.level = educationRequest.getLevel();
        this.faculty = educationRequest.getFaculty();
        this.dateOfGraduation = educationRequest.getDateOfGraduation();
        this.additionalInformation = educationRequest.getAdditionalInformation();
    }
}

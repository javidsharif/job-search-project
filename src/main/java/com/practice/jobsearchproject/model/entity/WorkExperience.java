package com.practice.jobsearchproject.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.practice.jobsearchproject.model.dto.request.WorkExperienceRequest;
import com.practice.jobsearchproject.utils.Updatable;
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
public class WorkExperience implements Updatable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String company;
    private String position;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfStartJob;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfEndJob;
    private boolean isContinuedJob;
    private String additionalInformation;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Override
    public <T> void update(T request) {
        WorkExperienceRequest workRequest = (WorkExperienceRequest) request;
        this.company = workRequest.getCompany();
        this.position = workRequest.getPosition();
        this.dateOfStartJob = workRequest.getDateOfStartJob();
        this.dateOfEndJob = workRequest.getDateOfEndJob();
        this.isContinuedJob = workRequest.isContinuedJob();
        this.additionalInformation = workRequest.getAdditionalInformation();
    }
}

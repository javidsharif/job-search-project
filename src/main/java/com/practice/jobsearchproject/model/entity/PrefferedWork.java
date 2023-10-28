package com.practice.jobsearchproject.model.entity;

import com.practice.jobsearchproject.model.dto.request.PrefferedWorkRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrefferedWork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String areaInWhichYouWork;
    private String jobGraphic;
    private String workLevel;
    private String studyLevel;
    private String prefferedSalary;
    private String currency;
    private String specialKnowledge;
    private String workExperience;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    public void updatePrefferedWork(PrefferedWorkRequest prefferedWorkRequest) {
        this.areaInWhichYouWork = prefferedWorkRequest.getAreaInWhichYouWork();
        this.jobGraphic = prefferedWorkRequest.getJobGraphic();
        this.workLevel = prefferedWorkRequest.getWorkLevel();
        this.studyLevel = prefferedWorkRequest.getStudyLevel();
        this.prefferedSalary = prefferedWorkRequest.getPrefferedSalary();
        this.currency = prefferedWorkRequest.getCurrency();
        this.specialKnowledge = prefferedWorkRequest.getSpecialKnowledge();
        this.workExperience = prefferedWorkRequest.getWorkExperience();
    }

}

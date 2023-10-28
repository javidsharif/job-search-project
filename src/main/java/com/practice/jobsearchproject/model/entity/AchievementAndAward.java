package com.practice.jobsearchproject.model.entity;

import com.practice.jobsearchproject.model.dto.request.AchievementAndAwardRequest;
import com.practice.jobsearchproject.utills.Updatable;
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
@Table(name = "acievement_and_award")
public class AchievementAndAward implements Updatable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String year;
    private String description;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Override
    public <T> void update(T request) {
        AchievementAndAwardRequest awardRequest = (AchievementAndAwardRequest) request;
        this.name = awardRequest.getName();
        this.year = awardRequest.getYear();
        this.description = awardRequest.getDescription();
    }
}




package com.practice.jobsearchproject.model.entity;

import com.practice.jobsearchproject.model.dto.request.LanguageRequest;
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
public class Language implements Updatable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String level;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Override
    public <T> void update(T request) {
        LanguageRequest languageRequest = (LanguageRequest) request;
        this.name = languageRequest.getName();
        this.level = languageRequest.getLevel();
    }
}

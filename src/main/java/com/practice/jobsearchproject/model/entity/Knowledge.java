package com.practice.jobsearchproject.model.entity;

import com.practice.jobsearchproject.model.dto.request.KnowledgeRequest;
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
public class Knowledge implements Updatable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer level;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Override
    public <T> void update(T request) {
        KnowledgeRequest knowledgeRequest = (KnowledgeRequest) request;
        this.name = knowledgeRequest.getName();
        this.level = knowledgeRequest.getLevel();
    }
}

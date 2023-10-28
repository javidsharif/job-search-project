package com.practice.jobsearchproject.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@Entity
@Table(name = "special_knowledge")
@AllArgsConstructor
public class SpecialKnowledge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @ManyToMany(mappedBy = "specialKnowledge")
    private Set<Vacancy> vacancies = new HashSet<>();
}

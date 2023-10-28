package com.practice.jobsearchproject.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@Entity
@Table(name = "job_types")
@AllArgsConstructor
public class JobType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    //    @OneToMany(mappedBy = "jobType")
//    private List<Vacancy> vacancies;
    @ManyToMany(mappedBy = "jobTypes")
//    @JsonIgnoreProperties("jobTypes")
//    @JsonBackReference
    private Set<Vacancy> vacancies = new HashSet<>();
}
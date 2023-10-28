package com.practice.jobsearchproject.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@Entity
@Table(name = "statuses")
@AllArgsConstructor
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "title")
    private String title;
    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private List<Vacancy> vacancies;
}

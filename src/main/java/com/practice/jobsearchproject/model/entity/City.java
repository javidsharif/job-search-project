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
@Table(name = "cities")
@AllArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    //    @OneToMany(mappedBy = "city")
//    @JsonIgnore
//    private List<Vacancy> vacancies;
    @ManyToMany(mappedBy = "cities")
//    @JsonIgnoreProperties("cities")
//    @JsonBackReference
    private Set<Vacancy> vacancies = new HashSet<>();
}

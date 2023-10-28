package com.practice.jobsearchproject.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@Entity
@Table(name = "communication_tools")
@AllArgsConstructor
public class CommunicationTool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "level")
    private String tool;
    @OneToMany(mappedBy = "communicationTool")
    private List<Vacancy> vacancies;
}

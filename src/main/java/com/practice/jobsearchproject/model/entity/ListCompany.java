package com.practice.jobsearchproject.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "list_companies")
public class ListCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @JsonProperty("id")
    private Long id;
//    @JsonProperty("name")
    private String name;
}

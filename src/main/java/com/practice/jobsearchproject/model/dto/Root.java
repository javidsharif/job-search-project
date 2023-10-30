package com.practice.jobsearchproject.model.dto;

import com.practice.jobsearchproject.model.entity.ListCompany;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.List;

@Getter
@Setter
public class Root {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private List<ListCompany> companies;

    @Override
    public String toString() {
        return "Root{" +
                "companies=" + companies +
                '}';
    }
}

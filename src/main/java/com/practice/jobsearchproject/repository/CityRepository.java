package com.practice.jobsearchproject.repository;

import com.practice.jobsearchproject.model.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    Set<City> findCityByIdIn(Set<Integer> ids);
}


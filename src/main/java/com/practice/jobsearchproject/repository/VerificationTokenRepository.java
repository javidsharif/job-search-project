package com.practice.jobsearchproject.repository;

import com.practice.jobsearchproject.model.entity.UserAuthentication;
import com.practice.jobsearchproject.model.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer> {
    VerificationToken findByToken(String token);

    Optional<VerificationToken> findByUserAuthentication(UserAuthentication userAuthentication);
}
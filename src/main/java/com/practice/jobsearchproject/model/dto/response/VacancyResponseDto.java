package com.practice.jobsearchproject.model.dto.response;


import com.practice.jobsearchproject.model.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacancyResponseDto {
    private long id;
    private String vacancyName;
    private Status status;
    private long viewNumber;
    private LocalDate createdAt;
    private LocalDate expiryDate;
}

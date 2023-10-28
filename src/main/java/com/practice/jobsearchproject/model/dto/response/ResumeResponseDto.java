package com.practice.jobsearchproject.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ResumeResponseDto {
    private Long id;
    private String position;
    private String status;
    private Long count;
    private String checkedComment;
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDateTime updateDate;
}

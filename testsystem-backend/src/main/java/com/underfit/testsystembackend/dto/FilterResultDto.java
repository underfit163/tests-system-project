package com.underfit.testsystembackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FilterResultDto {
    private Long testId;
    private Long userId;
    private Integer score;
    private Boolean acceptResult;
    private Integer age;
    private Integer countYearWork;
    private Long workNumber;
}

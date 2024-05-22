package com.underfit.testsystembackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.underfit.testsystembackend.entity.Result}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultDto implements Serializable {
    private Long id;
    private Integer score;
    private Boolean acceptResult;
    private UserDto user;
    private TestDto test;
    private AssessmentDto assessment;
}
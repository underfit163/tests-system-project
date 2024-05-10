package com.underfit.testsystembackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * DTO for {@link com.underfit.testsystembackend.entity.Test}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestWithQuestionDto implements Serializable {
    private Long id;
    private String testName;
    private String description;
    private Set<QuestionWithOptionDto> questions = new LinkedHashSet<>();
}
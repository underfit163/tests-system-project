package com.underfit.testsystembackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "assessment")
public class Assessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assessment_id", nullable = false)
    private Long id;

    @Column(name = "assessment_name", nullable = false, length = Integer.MAX_VALUE)
    private String assessmentName;

    @Column(name = "min_score")
    private Integer minScore;

    @Column(name = "max_score")
    private Integer maxScore;

    @Column(name = "result_description", length = Integer.MAX_VALUE)
    private String resultDescription;

}
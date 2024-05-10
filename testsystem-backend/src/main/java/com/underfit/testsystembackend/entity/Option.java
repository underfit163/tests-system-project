package com.underfit.testsystembackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "option")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id", nullable = false)
    private Long id;

    @Column(name = "option_text", nullable = false, length = Integer.MAX_VALUE)
    private String optionText;

    @Column(name = "score", nullable = false)
    private Integer score;

}
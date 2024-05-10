package com.underfit.testsystembackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "test")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id", nullable = false)
    private Long id;

    @Column(name = "test_name", nullable = false, length = Integer.MAX_VALUE)
    private String testName;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

}
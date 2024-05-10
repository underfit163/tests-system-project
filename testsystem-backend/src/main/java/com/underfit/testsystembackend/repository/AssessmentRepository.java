package com.underfit.testsystembackend.repository;

import com.underfit.testsystembackend.entity.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
}
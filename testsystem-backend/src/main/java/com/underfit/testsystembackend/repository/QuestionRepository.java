package com.underfit.testsystembackend.repository;

import com.underfit.testsystembackend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
package com.underfit.testsystembackend.repository;

import com.underfit.testsystembackend.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
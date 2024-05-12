package com.underfit.testsystembackend.repository;

import com.underfit.testsystembackend.entity.Answer;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByResult_Id(Long id, Sort sort);
}
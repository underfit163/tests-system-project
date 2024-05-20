package com.underfit.testsystembackend.repository;

import com.underfit.testsystembackend.entity.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {

    @Query("select a from Assessment a where a.test.id = :id and a.minScore <= :score and a.maxScore >= :score")
    Optional<Assessment> findAssessmentByTestIdAndScore(@Param("id") @NonNull Long id, @Param("score") @NonNull Integer score);
}
package com.underfit.testsystembackend.repository;

import com.underfit.testsystembackend.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<Result, Long> {
}
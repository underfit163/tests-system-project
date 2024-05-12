package com.underfit.testsystembackend.repository;

import com.underfit.testsystembackend.entity.Result;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long>, JpaSpecificationExecutor<Result> {
    List<Result> findByTest_Id(Long id, Sort sort);
}
package com.underfit.testsystembackend.repository;

import com.underfit.testsystembackend.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {
}
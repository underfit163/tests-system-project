package com.underfit.testsystembackend.repository;

import com.underfit.testsystembackend.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long> {
}
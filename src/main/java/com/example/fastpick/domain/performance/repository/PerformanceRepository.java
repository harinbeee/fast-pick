package com.example.fastpick.domain.performance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fastpick.domain.performance.model.Performance;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {
}

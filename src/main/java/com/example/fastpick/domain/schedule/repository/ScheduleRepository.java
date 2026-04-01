package com.example.fastpick.domain.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fastpick.domain.schedule.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}

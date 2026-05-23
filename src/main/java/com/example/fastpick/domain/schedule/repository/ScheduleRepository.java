package com.example.fastpick.domain.schedule.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.example.fastpick.domain.schedule.model.Schedule;

import jakarta.persistence.LockModeType;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("""
		SELECT s
		from Schedule s
		WHERE s.id = :id
		""")
	Optional<Schedule> findByIdWithPessimisticLock(Long id);
}

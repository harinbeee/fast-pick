package com.example.fastpick.domain.schedule.model;

import java.time.LocalDateTime;

import com.example.fastpick.domain.performance.model.Performance;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "schedules")
public class Schedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "performance_id",nullable = false)
	private Performance performance;

	@Column(nullable = false)
	private LocalDateTime openTime;

	@Column(nullable = false)
	private LocalDateTime performTime;

	@Column(nullable = false)
	private int totalSeats;

	@Column(nullable = false)
	private int availableSeats;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ScheduleStatus status;

	private Schedule(Performance performance, LocalDateTime openTime, LocalDateTime performTime, int totalSeats) {
		this.performance = performance;
		this.openTime = openTime;
		this.performTime = performTime;
		this.totalSeats = totalSeats;
		this.availableSeats = totalSeats; // 생성될 때 잔여 좌석은 총 좌석과 동일하게 세팅!
		this.status = ScheduleStatus.WAITING; // 생성될 때는 무조건 오픈 대기 상태!
	}

	public static Schedule create(Performance performance, LocalDateTime openTime, LocalDateTime performTime, int totalSeats) {
		return new Schedule(performance, openTime, performTime, totalSeats);
	}

	public void decreaseSeats() {
		if(this.availableSeats <=0) {
			throw new IllegalStateException("잔여 좌석이 없습니다.");
		}
		this.availableSeats--;
	}

	public int getAvailableSeats() {
		return availableSeats;
	}

}

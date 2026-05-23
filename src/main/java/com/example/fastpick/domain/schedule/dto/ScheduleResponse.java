package com.example.fastpick.domain.schedule.dto;

import java.time.LocalDateTime;

import com.example.fastpick.domain.schedule.model.Schedule;

import jakarta.validation.constraints.NotNull;

public record ScheduleResponse(

	String performanceName,
	LocalDateTime openTime,
	LocalDateTime performTime,
	int totalSeats
) {
	public static ScheduleResponse from(Schedule schedule) {
		return new ScheduleResponse(
			schedule.getPerformance().getTitle(),
			schedule.getOpenTime(),
			schedule.getPerformTime(),
			schedule.getTotalSeats()
		);
	}
}

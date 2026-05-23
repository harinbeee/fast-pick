package com.example.fastpick.domain.schedule.dto;

import java.time.LocalDateTime;

import com.example.fastpick.domain.performance.model.Performance;

import jakarta.validation.constraints.NotNull;

public record ScheduleRequest(
	@NotNull(message = "공연 아이디는 필수입니다.")
	Long performanceId,

	@NotNull(message = "예매 오픈 시간 필수입니다.")
	LocalDateTime openTime,

	@NotNull(message = "공연 시작 시간은 필수입니다.")
	LocalDateTime performTime,

	@NotNull(message = "전체 좌석 수는 필수입니다.")
	int totalSeats
) {
}

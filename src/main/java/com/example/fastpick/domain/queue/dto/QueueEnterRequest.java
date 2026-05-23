package com.example.fastpick.domain.queue.dto;

import jakarta.validation.constraints.NotNull;

public record QueueEnterRequest(
	@NotNull(message = "회차 아이디는 필수입니다.")
	Long scheduleId,
	@NotNull(message = "유저 아이디는 필수입니다.")
	Long userId
) {
}


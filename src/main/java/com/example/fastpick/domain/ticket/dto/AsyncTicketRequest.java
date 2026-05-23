package com.example.fastpick.domain.ticket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AsyncTicketRequest(
	@NotBlank(message = "requestId는 필수입니다.")
	String requestId,
	@NotNull(message = "회차 아이디는 필수입니다.")
	Long scheduleId,
	@NotNull(message = "유저 아이디는 필수입니다.")
	Long userId
) {
}


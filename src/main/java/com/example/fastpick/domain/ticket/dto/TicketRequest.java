package com.example.fastpick.domain.ticket.dto;

import jakarta.validation.constraints.NotNull;

public record TicketRequest(
	@NotNull (message = "회차 아이디는 필수입니다.")
	Long scheduleId
) {
}

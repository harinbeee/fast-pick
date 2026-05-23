package com.example.fastpick.domain.ticket.dto;

import com.example.fastpick.domain.ticket.model.TicketRequestStatus;

public record TicketRequestStatusResponse(
	String requestId,
	Long scheduleId,
	Long userId,
	TicketRequestStatus status,
	Long ticketId,
	String failReason
) {
}


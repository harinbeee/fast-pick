package com.example.fastpick.domain.ticket.kafka;

public record TicketRequestedEvent(
	String requestId,
	Long scheduleId,
	Long userId
) {
}


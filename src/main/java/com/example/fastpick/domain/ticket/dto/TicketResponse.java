package com.example.fastpick.domain.ticket.dto;

import com.example.fastpick.domain.ticket.model.Ticket;
import com.example.fastpick.domain.ticket.model.TicketStatus;

public record TicketResponse (
	String title,
	Long ticketId,
	Long scheduleId,
	Long userId,
	TicketStatus status
){
	public static TicketResponse from(Ticket ticket) {
		return new TicketResponse(
			ticket.getSchedule().getPerformance().getTitle(),
			ticket.getId(),
			ticket.getSchedule().getId(),
			ticket.getUserId(),
			ticket.getStatus()
		);
	}
}

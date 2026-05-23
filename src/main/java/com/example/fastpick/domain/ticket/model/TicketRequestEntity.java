package com.example.fastpick.domain.ticket.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ticket_requests")
public class TicketRequestEntity {

	@Id
	@Column(length = 64)
	private String requestId;

	@Column(nullable = false)
	private Long scheduleId;

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TicketRequestStatus status;

	@Column
	private Long ticketId;

	@Column(length = 500)
	private String failReason;

	private TicketRequestEntity(String requestId, Long scheduleId, Long userId) {
		this.requestId = requestId;
		this.scheduleId = scheduleId;
		this.userId = userId;
		this.status = TicketRequestStatus.PROCESSING;
	}

	public static TicketRequestEntity create(String requestId, Long scheduleId, Long userId) {
		return new TicketRequestEntity(requestId, scheduleId, userId);
	}

	public void succeed(Long ticketId) {
		this.status = TicketRequestStatus.SUCCEEDED;
		this.ticketId = ticketId;
		this.failReason = null;
	}

	public void fail(String reason) {
		this.status = TicketRequestStatus.FAILED;
		this.failReason = reason;
	}
}


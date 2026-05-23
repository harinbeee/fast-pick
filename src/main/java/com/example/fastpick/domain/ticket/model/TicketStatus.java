package com.example.fastpick.domain.ticket.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TicketStatus {
	PENDING("결제 대기"),
	CONFIRMED("예매 완료"),
	CANCELED("취소");


	private final String  description;
	}

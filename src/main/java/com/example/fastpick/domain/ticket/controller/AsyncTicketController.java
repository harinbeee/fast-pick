package com.example.fastpick.domain.ticket.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.fastpick.domain.ticket.dto.TicketRequestStatusResponse;
import com.example.fastpick.domain.ticket.service.AsyncTicketingService;

import lombok.RequiredArgsConstructor;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/tickets-async")
public class AsyncTicketController {

	private final AsyncTicketingService asyncTicketingService;

	/**
	 * 대기열에서 permit을 받은 requestId로만 예매 요청을 접수한다.
	 * 실제 DB write는 Kafka consumer가 수행.
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.ACCEPTED)
	public TicketRequestStatusResponse request(
		@RequestParam Long scheduleId,
		@RequestParam Long userId,
		@RequestParam String requestId
	) {
		return asyncTicketingService.requestTicket(scheduleId, userId, requestId);
	}

	@GetMapping("/status/{requestId}")
	public TicketRequestStatusResponse status(@PathVariable String requestId) {
		return asyncTicketingService.getStatus(requestId);
	}
}


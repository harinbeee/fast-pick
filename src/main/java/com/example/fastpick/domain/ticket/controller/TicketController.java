package com.example.fastpick.domain.ticket.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.fastpick.domain.ticket.dto.TicketRequest;
import com.example.fastpick.domain.ticket.dto.TicketResponse;
import com.example.fastpick.domain.ticket.service.TicketService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/tickets")
public class TicketController {

	private final TicketService ticketService;

	@PostMapping()
	public TicketResponse reserveTicket(
		@RequestBody TicketRequest request
	) {
		return ticketService.reserveTicket(request);
	}

}

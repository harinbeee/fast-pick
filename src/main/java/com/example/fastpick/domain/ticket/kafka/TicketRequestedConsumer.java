package com.example.fastpick.domain.ticket.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.fastpick.domain.ticket.dto.TicketRequest;
import com.example.fastpick.domain.ticket.dto.TicketResponse;
import com.example.fastpick.domain.ticket.model.TicketRequestEntity;
import com.example.fastpick.domain.ticket.repository.TicketRequestRepository;
import com.example.fastpick.domain.ticket.service.AsyncTicketingService;
import com.example.fastpick.domain.ticket.service.TicketService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TicketRequestedConsumer {

	private final TicketService ticketService;
	private final TicketRequestRepository ticketRequestRepository;
	private final AsyncTicketingService asyncTicketingService;

	@Value("${fastpick.kafka.topics.ticket-requested:ticket-requested}")
	private String topic;

	@KafkaListener(
		topics = "${fastpick.kafka.topics.ticket-requested:ticket-requested}",
		containerFactory = "ticketRequestedKafkaListenerContainerFactory"
	)
	@Transactional
	public void handle(TicketRequestedEvent event) {
		TicketRequestEntity entity = ticketRequestRepository.findById(event.requestId())
			.orElseThrow(() -> new IllegalStateException("ticket_requests row not found: " + event.requestId()));

		try {
			TicketResponse ticket = ticketService.reserveTicket(new TicketRequest(event.scheduleId(), event.userId()));
			entity.succeed(ticket.ticketId());
		} catch (Exception e) {
			entity.fail(e.getMessage());
		} finally {
			// permit은 처리 완료 후 해제(성공/실패 모두)
			asyncTicketingService.releasePermit(event.scheduleId(), event.requestId());
		}
	}
}


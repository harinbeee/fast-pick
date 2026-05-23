package com.example.fastpick.domain.ticket.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TicketEventProducer {

	private final KafkaTemplate<String, TicketRequestedEvent> kafkaTemplate;

	@Value("${fastpick.kafka.topics.ticket-requested:ticket-requested}")
	private String topic;

	public void publish(TicketRequestedEvent event) {
		kafkaTemplate.send(topic, event.requestId(), event);
	}
}


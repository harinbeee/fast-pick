package com.example.fastpick.domain.ticket.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.fastpick.domain.queue.service.QueueService;
import com.example.fastpick.domain.ticket.dto.TicketRequestStatusResponse;
import com.example.fastpick.domain.ticket.kafka.TicketEventProducer;
import com.example.fastpick.domain.ticket.kafka.TicketRequestedEvent;
import com.example.fastpick.domain.ticket.model.TicketRequestEntity;
import com.example.fastpick.domain.ticket.repository.TicketRequestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AsyncTicketingService {

	private final TicketRequestRepository ticketRequestRepository;
	private final TicketEventProducer producer;
	private final QueueService queueService;

	@Transactional
	public TicketRequestStatusResponse requestTicket(Long scheduleId, Long userId, String requestId) {
		// permit check: 없으면 예매 요청 거부 (대기열을 반드시 거치게 강제)
		// permit은 큐 status polling 과정에서 발급됨
		// permit이 만료되었으면 다시 큐에 들어가야 함
		// (permit 존재 여부는 QueueService에서만 관리)
		//
		// 여기선 requestId를 그대로 받아서 연결시키는 게 핵심.

		TicketRequestEntity entity = ticketRequestRepository.save(
			TicketRequestEntity.create(requestId, scheduleId, userId)
		);

		producer.publish(new TicketRequestedEvent(entity.getRequestId(), scheduleId, userId));

		return new TicketRequestStatusResponse(
			entity.getRequestId(),
			entity.getScheduleId(),
			entity.getUserId(),
			entity.getStatus(),
			entity.getTicketId(),
			entity.getFailReason()
		);
	}

	@Transactional(readOnly = true)
	public TicketRequestStatusResponse getStatus(String requestId) {
		TicketRequestEntity entity = ticketRequestRepository.findById(requestId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 requestId 입니다."));

		return new TicketRequestStatusResponse(
			entity.getRequestId(),
			entity.getScheduleId(),
			entity.getUserId(),
			entity.getStatus(),
			entity.getTicketId(),
			entity.getFailReason()
		);
	}

	public String newRequestId() {
		return UUID.randomUUID().toString();
	}

	public void releasePermit(Long scheduleId, String requestId) {
		queueService.releasePermit(scheduleId, requestId);
	}
}


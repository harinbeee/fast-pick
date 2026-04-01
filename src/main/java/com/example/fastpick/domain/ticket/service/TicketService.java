package com.example.fastpick.domain.ticket.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.fastpick.domain.schedule.model.Schedule;
import com.example.fastpick.domain.schedule.model.ScheduleStatus;
import com.example.fastpick.domain.schedule.repository.ScheduleRepository;
import com.example.fastpick.domain.ticket.dto.TicketRequest;
import com.example.fastpick.domain.ticket.dto.TicketResponse;
import com.example.fastpick.domain.ticket.model.Ticket;
import com.example.fastpick.domain.ticket.repository.TicketRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TicketService {

	private final TicketRepository ticketRepository;
	private final ScheduleRepository scheduleRepository;

	/*
	티켓 예매
	 */
	@Transactional
	public TicketResponse reserveTicket(TicketRequest request) {
		// 1. 유저검증(현재 생략)
		// 2. 회차 찾기
		Long scheduleId = request.scheduleId();
		Schedule targetSche = scheduleRepository.findById(scheduleId)
			.orElseThrow(()-> new RuntimeException());

		if(targetSche.getStatus()!= ScheduleStatus.OPEN) {
			throw new IllegalStateException("예매중이 아닙니다.");
		}

		//3. 잔여석 확인
		targetSche.decreaseSeats();

		Ticket ticket = Ticket.create(targetSche, request.userId());
		ticketRepository.save(ticket);

		return TicketResponse.from(ticket);

	}
}

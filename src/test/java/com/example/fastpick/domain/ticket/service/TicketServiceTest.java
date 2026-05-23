package com.example.fastpick.domain.ticket.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.fastpick.domain.schedule.model.Schedule;
import com.example.fastpick.domain.schedule.repository.ScheduleRepository;
import com.example.fastpick.domain.ticket.dto.TicketRequest;
import com.example.fastpick.domain.ticket.dto.TicketResponse;

@SpringBootTest
class TicketServiceTest {

	@Autowired
	private TicketService ticketService;

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Test
	void 동시에_100명의_유저가_1번스케줄_예매시도() throws  InterruptedException {
		//given
		int threadCount = 100;
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch latch = new CountDownLatch(threadCount);

		Long targetScheId = 1L;
		// when
		for(int i = 0; i < threadCount; i++ ) {
			long userId = i + 1; // id 0 회피
			executorService.submit(()-> {
				try{
					TicketRequest request = new TicketRequest(targetScheId, userId);
					TicketResponse ticketResponse = ticketService.reserveTicket(request);
					System.out.println("✅티켓 구매 성공 | 유저 ID : "+ticketResponse.userId()+" 티켓 ID : " + ticketResponse.ticketId());
				} finally {
					latch.countDown();
				}
			});
		}
		latch.await();

		//then
		Schedule schedule = scheduleRepository.findById(targetScheId).orElseThrow();
		int availableSeats = schedule.getAvailableSeats();

		System.out.println("남은 좌석 수 : " + availableSeats);

		assertThat(availableSeats).isEqualTo(0);
	}

}
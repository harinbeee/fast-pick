package com.example.fastpick.domain.ticket.model;

import com.example.fastpick.domain.schedule.model.Schedule;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tickets")
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "schedule_id")
	private Schedule schedule;

	@Column
	private Long userId; // 나중에 유저 구현하면 바꾸자~!

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TicketStatus status;

	private Ticket(Schedule schedule, Long userId) {
		this.schedule = schedule;
		this.userId = userId;
		this.status = TicketStatus.PENDING; // 티켓 생성 시 최초 상태는 결제 대기
	}

	public static Ticket create(Schedule schedule, Long userId) {
		return new Ticket(schedule, userId);
	}

}

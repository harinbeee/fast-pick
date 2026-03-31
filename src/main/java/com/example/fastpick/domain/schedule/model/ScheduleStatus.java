package com.example.fastpick.domain.schedule.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ScheduleStatus {
	WAITING("오픈 대기"),
	OPEN("예매 중"),
	SOLD_OUT("매진"),
	CLOSED("예매 종료");

	private final String description;
}

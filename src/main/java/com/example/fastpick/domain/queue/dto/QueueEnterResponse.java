package com.example.fastpick.domain.queue.dto;

public record QueueEnterResponse(
	String requestId,
	Long scheduleId,
	Long userId,
	long position
) {
}


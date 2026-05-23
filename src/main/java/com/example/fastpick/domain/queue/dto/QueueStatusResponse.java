package com.example.fastpick.domain.queue.dto;

public record QueueStatusResponse(
	String requestId,
	Long scheduleId,
	Long userId,
	QueueState state,
	Long position,
	boolean hasPermit
) {
	public enum QueueState {
		WAITING,
		ENTERED,
		EXPIRED
	}
}


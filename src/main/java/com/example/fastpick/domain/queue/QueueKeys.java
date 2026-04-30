package com.example.fastpick.domain.queue;

public final class QueueKeys {

	private QueueKeys() {}

	public static String queueKey(Long scheduleId) {
		return "queue:schedule:" + scheduleId;
	}

	public static String permitKey(String requestId) {
		return "permit:" + requestId;
	}

	public static String activePermitsKey(Long scheduleId) {
		return "queue:active:" + scheduleId;
	}
}


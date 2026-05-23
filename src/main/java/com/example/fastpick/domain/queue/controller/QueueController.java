package com.example.fastpick.domain.queue.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.fastpick.domain.queue.dto.QueueEnterRequest;
import com.example.fastpick.domain.queue.dto.QueueEnterResponse;
import com.example.fastpick.domain.queue.dto.QueueStatusResponse;
import com.example.fastpick.domain.queue.service.QueueService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/queue")
public class QueueController {

	private final QueueService queueService;

	@PostMapping("/enter")
	public QueueEnterResponse enter(@Validated @RequestBody QueueEnterRequest req) {
		return queueService.enter(req);
	}

	@GetMapping("/status/{requestId}")
	public QueueStatusResponse status(
		@PathVariable String requestId,
		@RequestParam Long scheduleId,
		@RequestParam Long userId
	) {
		return queueService.status(scheduleId, userId, requestId);
	}
}


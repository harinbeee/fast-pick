package com.example.fastpick.domain.performance.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.fastpick.domain.performance.dto.PerformanceRequest;
import com.example.fastpick.domain.performance.dto.PerformanceResponse;
import com.example.fastpick.domain.performance.service.PerformanceService;

import lombok.AllArgsConstructor;

@RequestMapping("/api/performances")
@RestController
@AllArgsConstructor
public class PerformanceController {

	private final PerformanceService performanceService;

	@PostMapping()
	public PerformanceResponse createPerform(
		@RequestBody PerformanceRequest request,
		Authentication authentication
	) {
		String userMail = authentication.getName();

		return performanceService.create(request, userMail);

	}

}

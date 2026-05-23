package com.example.fastpick.domain.performance.dto;

import com.example.fastpick.domain.performance.model.Category;
import com.example.fastpick.domain.performance.model.Performance;

import jakarta.validation.constraints.NotNull;

public record PerformanceResponse(
	Category category,

	String title,

	String venue,

	String description,

	int price
) {
	public static PerformanceResponse from (Performance performance){
		return new PerformanceResponse(
			performance.getCategory(),
			performance.getTitle(),
			performance.getVenue(),
			performance.getDescription(),
			performance.getPrice()
		);
	}
}

package com.example.fastpick.domain.performance.dto;

import com.example.fastpick.domain.performance.model.Category;

import jakarta.validation.constraints.NotNull;

public record PerformanceRequest(

	@NotNull (message = "카테고리는 필수입니다.")
	Category category,

	@NotNull (message = "공연 제목은 필수입니다.")
	String title,

	@NotNull (message = "공연장 이름은 필수입니다.")
	String venue,

	String description,

	@NotNull (message = "티켓 가격은 필수입니다.")
	int price
) {
}

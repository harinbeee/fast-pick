package com.example.fastpick.domain.performance.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
	SPORT("스포츠"),
	CONCERT("콘서트"),
	MUSICAL("뮤지컬"),
	EXHIBITION("전시회");

	private final String description;
}

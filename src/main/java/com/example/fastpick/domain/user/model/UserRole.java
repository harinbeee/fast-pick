package com.example.fastpick.domain.user.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
	CUSTOMER("고객"),
	ADMIN("관리자");

	private final String description;
}


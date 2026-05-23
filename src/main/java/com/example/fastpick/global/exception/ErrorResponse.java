package com.example.fastpick.global.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(

	LocalDateTime timestamp,
	int status,
	String error,
	String message,
	List<FieldErrorDetail> errors
) {
	public ErrorResponse(int status, String error, String message) {
		this(LocalDateTime.now(), status, error, message, null );
	}

	public ErrorResponse(int status, String error, String message, List<FieldErrorDetail> errors) {
		this(LocalDateTime.now(), status, error, message, errors );
	}

	public record FieldErrorDetail (
		String field,
		String rejectedValue,
		String reason
	) {}
}

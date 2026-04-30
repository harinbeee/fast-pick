package com.example.fastpick.domain.user.dto;

import com.example.fastpick.domain.user.model.UserRole;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;

public record SignUpRequestDto (
	@NotBlank(message = "유저 메일은 필수 입니다.")
	String mail,
	@NotBlank(message = "비밀번호는 필수 입니다.")
	String password,
	@NotBlank(message = "유저 닉네임은 필수 입니다.")
	String name,
	@NotBlank(message = "유저 권한은 필수 입니다.")
	@Enumerated
	UserRole role
){
}

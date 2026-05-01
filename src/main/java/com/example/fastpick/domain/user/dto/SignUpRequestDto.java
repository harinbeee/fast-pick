package com.example.fastpick.domain.user.dto;

import com.example.fastpick.domain.user.model.UserRole;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SignUpRequestDto (
	@NotBlank(message = "유저 메일은 필수 입니다.")
	@Email(message = "이메일 형식이 올바르지 않습니다.")
	String mail,

	@NotBlank(message = "비밀번호는 필수 입니다.")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,15}$",
		message = "비밀번호는 8~15자리 영문, 숫자, 특수문자를 포함해야 합니다.")
	String password,

	@NotBlank(message = "유저 닉네임은 필수 입니다.")
	String name,

	@NotBlank(message = "유저 권한은 필수 입니다.")
	@Enumerated
	UserRole role
){
}

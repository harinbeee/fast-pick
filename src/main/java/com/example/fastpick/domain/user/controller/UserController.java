package com.example.fastpick.domain.user.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.fastpick.domain.user.dto.LoginRequestDto;
import com.example.fastpick.domain.user.dto.SignUpRequestDto;
import com.example.fastpick.domain.user.dto.UserResponseDto;
import com.example.fastpick.domain.user.model.User;
import com.example.fastpick.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/signup")
	public UserResponseDto signUp (
		@RequestBody SignUpRequestDto requestDto
	){
		UserResponseDto responseDto = userService.createUser(requestDto);
		return responseDto;
	}

	@PostMapping("/login")
	public UserResponseDto login (
		@RequestBody LoginRequestDto requestDto
	){
		UserResponseDto responseDto = userService.loginUser(requestDto);
		return responseDto;
	}
}

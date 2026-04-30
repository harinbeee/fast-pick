package com.example.fastpick.domain.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.fastpick.domain.user.dto.LoginRequestDto;
import com.example.fastpick.domain.user.dto.SignUpRequestDto;
import com.example.fastpick.domain.user.dto.UserResponseDto;
import com.example.fastpick.domain.user.model.User;
import com.example.fastpick.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public UserResponseDto createUser (SignUpRequestDto requestDto) {
		if(userRepository.existsByMail(requestDto.mail())) {
			throw new RuntimeException("이미 사용중인 메일 계정입니다.");
		}

		User user = new User(requestDto.mail(),requestDto.password(),requestDto.name(),requestDto.role());
		userRepository.save(user);
		UserResponseDto userResponseDto = new UserResponseDto(user.getMail(), user.getName());
		return userResponseDto;
	}

	public UserResponseDto loginUser (LoginRequestDto requestDto) {
		User user = userRepository.findByMail(requestDto.mail());
		if(user.getPassword() == requestDto.password()) {
			UserResponseDto response = new UserResponseDto(user.getMail(), user.getName());
			return response;
		} else {
			throw new RuntimeException("비밀번호가 일치하지 않습니다.");
		}
	}


}

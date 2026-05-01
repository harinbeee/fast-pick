package com.example.fastpick.domain.user.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
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
	private final PasswordEncoder passwordEncoder;

	public UserResponseDto createUser (SignUpRequestDto requestDto) {
		if(userRepository.existsByMail(requestDto.mail())) {
			throw new RuntimeException("이미 사용중인 메일 계정입니다.");
		}

		String encodedPassword = passwordEncoder.encode(requestDto.password());

		User user = new User(requestDto.mail(),encodedPassword,requestDto.name(),requestDto.role());
		userRepository.save(user);
		return new UserResponseDto(user.getMail(), user.getName());
	}

	public UserResponseDto loginUser (LoginRequestDto requestDto) {
		User user = userRepository.findByMail(requestDto.mail())
			.orElseThrow(()-> new IllegalArgumentException("가입되지 않은 이메일입니다."));

		if(passwordEncoder.matches(requestDto.password(), user.getPassword())) {
			return new UserResponseDto(user.getMail(), user.getName());
		} else {
			throw new RuntimeException("비밀번호가 일치하지 않습니다.");
		}
	}


}

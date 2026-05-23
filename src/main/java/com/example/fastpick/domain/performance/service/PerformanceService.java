package com.example.fastpick.domain.performance.service;

import org.springframework.stereotype.Service;

import com.example.fastpick.domain.performance.dto.PerformanceRequest;
import com.example.fastpick.domain.performance.dto.PerformanceResponse;
import com.example.fastpick.domain.performance.model.Performance;
import com.example.fastpick.domain.performance.repository.PerformanceRepository;
import com.example.fastpick.domain.user.model.User;
import com.example.fastpick.domain.user.model.UserRole;
import com.example.fastpick.domain.user.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PerformanceService {

	private final UserRepository userRepository;
	private final PerformanceRepository performanceRepository;

	public PerformanceResponse create(PerformanceRequest request, String mail) {
		// 1. 유저 찾기
		User user = userRepository.findByMail(mail)
			.orElseThrow(()-> new IllegalArgumentException("유저 정보를 찾을 수 없습니다."));

		// 2. 유저 권한 확인
		if(user.getRole() == UserRole.CUSTOMER) {
			throw new IllegalArgumentException("공연 생성 권한이 없습니다.");
		}

		// 3. JPA에 저장
		Performance performance = Performance.create(
			request.category(),
			request.title(),
			request.venue(),
			request.description(),
			request.price()
		);

		performanceRepository.save(performance);

		return PerformanceResponse.from(performance);
	}

}

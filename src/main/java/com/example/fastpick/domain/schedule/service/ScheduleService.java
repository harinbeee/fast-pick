package com.example.fastpick.domain.schedule.service;

import org.springframework.stereotype.Service;

import com.example.fastpick.domain.performance.model.Performance;
import com.example.fastpick.domain.performance.repository.PerformanceRepository;
import com.example.fastpick.domain.schedule.dto.ScheduleRequest;
import com.example.fastpick.domain.schedule.dto.ScheduleResponse;
import com.example.fastpick.domain.schedule.model.Schedule;
import com.example.fastpick.domain.schedule.repository.ScheduleRepository;
import com.example.fastpick.domain.user.model.User;
import com.example.fastpick.domain.user.model.UserRole;
import com.example.fastpick.domain.user.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScheduleService {

	private final ScheduleRepository scheduleRepository;
	private final UserRepository userRepository;
	private final PerformanceRepository performanceRepository;

	public ScheduleResponse create(ScheduleRequest request, String mail) {
		// 1. 유저 찾기
		User user = userRepository.findByMail(mail)
			.orElseThrow(()-> new IllegalArgumentException("유저 정보를 찾을 수 없습니다."));

		// 2. 유저 권한 확인
		if(user.getRole() == UserRole.CUSTOMER) {
			throw new IllegalArgumentException("회차 생성 권한이 없습니다.");
		}

		// 3. 공연 찾기
		Performance performance = performanceRepository.findById(request.performanceId())
			.orElseThrow(()-> new IllegalArgumentException("해당 아이디의 공연을 찾을 수 없습니다."));

		// 4. JPA에 저장
		Schedule schedule = Schedule.create(performance, request.openTime(), request.performTime(), request.totalSeats());
		scheduleRepository.save(schedule);

		return ScheduleResponse.from(schedule);
	}
}

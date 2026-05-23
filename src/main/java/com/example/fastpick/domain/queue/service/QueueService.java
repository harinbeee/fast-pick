package com.example.fastpick.domain.queue.service;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import com.example.fastpick.domain.queue.QueueKeys;
import com.example.fastpick.domain.queue.dto.QueueEnterRequest;
import com.example.fastpick.domain.queue.dto.QueueEnterResponse;
import com.example.fastpick.domain.queue.dto.QueueStatusResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QueueService {

	private final StringRedisTemplate redis;

	@Value("${fastpick.queue.enter-window-size:50}")
	private int enterWindowSize;

	@Value("${fastpick.queue.max-active-permits-per-schedule:20}")
	private int maxActivePermitsPerSchedule;

	@Value("${fastpick.queue.permit-ttl-seconds:30}")
	private int permitTtlSeconds;

	private static final DefaultRedisScript<Long> TRY_ENTER_SCRIPT = new DefaultRedisScript<>(
		"""
		-- KEYS[1] queueKey (zset)
		-- KEYS[2] activePermitsKey (string counter)
		-- KEYS[3] permitKey (string)
		-- ARGV[1] member (requestId)
		-- ARGV[2] nowScore
		-- ARGV[3] enterWindowSize
		-- ARGV[4] maxActivePermits
		-- ARGV[5] permitTtlSeconds

		local queueKey = KEYS[1]
		local activeKey = KEYS[2]
		local permitKey = KEYS[3]

		local member = ARGV[1]
		local nowScore = tonumber(ARGV[2])
		local windowSize = tonumber(ARGV[3])
		local maxActive = tonumber(ARGV[4])
		local ttlSec = tonumber(ARGV[5])

		-- if permit already exists, return 1
		if redis.call('EXISTS', permitKey) == 1 then
		  return 1
		end

		-- if not in queue, not eligible
		local rank = redis.call('ZRANK', queueKey, member)
		if rank == false then
		  return 0
		end

		-- only top window can try to enter
		if rank >= windowSize then
		  return 0
		end

		local active = tonumber(redis.call('GET', activeKey) or '0')
		if active >= maxActive then
		  return 0
		end

		-- grant permit: remove from queue, increment active, set permit with ttl
		redis.call('ZREM', queueKey, member)
		redis.call('INCR', activeKey)
		redis.call('SET', permitKey, '1', 'EX', ttlSec)
		return 1
		""",
		Long.class
	);

	public QueueEnterResponse enter(QueueEnterRequest req) {
		String requestId = UUID.randomUUID().toString();
		String memberValue = member(req.scheduleId(), req.userId(), requestId);
		long score = System.currentTimeMillis();

		redis.opsForZSet().add(QueueKeys.queueKey(req.scheduleId()), memberValue, score);
		Long rank = redis.opsForZSet().rank(QueueKeys.queueKey(req.scheduleId()), memberValue);
		long position = rank == null ? -1 : rank + 1;

		return new QueueEnterResponse(requestId, req.scheduleId(), req.userId(), position);
	}

	public QueueStatusResponse status(Long scheduleId, Long userId, String requestId) {
		String memberValue = member(scheduleId, userId, requestId);
		String queueKey = QueueKeys.queueKey(scheduleId);

		boolean hasPermit = Boolean.TRUE.equals(redis.hasKey(QueueKeys.permitKey(requestId)));

		Long rank = redis.opsForZSet().rank(queueKey, memberValue);
		if (rank == null) {
			return new QueueStatusResponse(
				requestId,
				scheduleId,
				userId,
				hasPermit ? QueueStatusResponse.QueueState.ENTERED : QueueStatusResponse.QueueState.EXPIRED,
				null,
				hasPermit
			);
		}

		// Try to enter when close enough; script does atomic "remove + permit + active++"
		if (!hasPermit && rank < enterWindowSize) {
			Long ok = redis.execute(
				TRY_ENTER_SCRIPT,
				List.of(queueKey, QueueKeys.activePermitsKey(scheduleId), QueueKeys.permitKey(requestId)),
				memberValue,
				Long.toString(System.currentTimeMillis()),
				Integer.toString(enterWindowSize),
				Integer.toString(maxActivePermitsPerSchedule),
				Integer.toString(permitTtlSeconds)
			);

			if (ok != null && ok == 1L) {
				return new QueueStatusResponse(
					requestId,
					scheduleId,
					userId,
					QueueStatusResponse.QueueState.ENTERED,
					null,
					true
				);
			}
		}

		return new QueueStatusResponse(
			requestId,
			scheduleId,
			userId,
			QueueStatusResponse.QueueState.WAITING,
			rank + 1,
			hasPermit
		);
	}

	public void releasePermit(Long scheduleId, String requestId) {
		redis.delete(QueueKeys.permitKey(requestId));
		String activeKey = QueueKeys.activePermitsKey(scheduleId);
		Long after = redis.opsForValue().decrement(activeKey);
		if (after != null && after <= 0) {
			redis.delete(activeKey);
		}
	}

	private static String member(Long scheduleId, Long userId, String requestId) {
		return "req:" + requestId + ":schedule:" + scheduleId + ":user:" + userId;
	}
}

